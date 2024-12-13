package com.example.demo.service

import com.example.demo.entity.ContentType
import com.example.demo.service.usecase.RegisterMessageSearchDataUseCase
import com.example.demo.utils.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.Document
import org.bson.types.ObjectId
import org.elasticsearch.client.RestClient
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterMessageSearchDataService(
    private val mongoTemplate: MongoTemplate,
    private val objectMapper: ObjectMapper,
    private val elasticClient: RestClient
) : RegisterMessageSearchDataUseCase {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun saveMessageData(document: Document) {
        try {
            val document = normalizeDocument(document)
            logger.info("Generated Normalized Document = [$document]")
            mongoTemplate.markIsComplete(document["_id"] as ObjectId, "message-event")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun normalizeDocument(document: Document): Map<String, Any?> {
        val contentType = ContentType.valueOf(document.getString("contentType"))
        val todo = document.getNestedObjectMap("content.todo")

        // TODO가 없으면 색인이 불가능
        if (contentType == ContentType.TODO && todo.isEmpty()) throw RuntimeException("Content Type = TODO, But it hasn't data")

        val title = extractTitle(contentType, document)
        val body = extractBody(contentType, document)

        val contents = if (contentType == ContentType.TODO || contentType == ContentType.POST) {
            "$title $body".trim()
        } else {
            body
        }

        val mentions = extractMentions(document)
        val attachmentIds = extractAttachmentIds(document)
        val attachmentNames = extractAttachmentNames(document)

        return createNormalizeDocument(document, contentType, contents, mentions, title, body, attachmentIds, attachmentNames)
    }

    private fun createNormalizeDocument(
        document: Document,
        contentType: ContentType,
        contents: String,
        mentions: String?,
        title: String,
        body: String,
        attachmentIds: List<Long>,
        attachmentNames: List<String>
    ): MutableMap<String, Any?> {
        val normalizeDocument = mutableMapOf(
            "_id" to document.get("_id", ObjectId::class.java),
            "id" to document.getNullableLongValue("id"),
            "permission" to document.getNullableIntValue("permission"),
            "teamId" to document.getNullableLongValue("teamId"),
            "writerId" to document.getNullableLongValue("writerId"),
            "feedbackId" to document.getNullableLongValue("feedbackId"),
            "shareEntities" to document.getGenericCollection<Long>("shareEntities"),
            "contentType" to contentType,
            "contents" to contents,
            "contents_pr" to contents,
            "mentionString" to mentions,
            "commentCount" to document.getNullableIntValue("commentCount"),
            "createdAt" to document.getLocalDateTimeValue("createdAt"),
            "updatedAt" to document.getLocalDateTimeValue("updatedAt"),
            "from" to document.getString("from"),
            "isThreaded" to document.getBoolean("isThreaded"),
            "isFormatted" to document.getBoolean("isFormatted"),
            "connectType" to document.getNestedNullableStringValue("content.connectType"),
            "title" to title,
            "body" to body,
            "text" to body.let { it.ifBlank { "(empty)" } },
            "attachmentIds" to attachmentIds,
            "attachmentNames" to attachmentNames,
            "sharedMessageIds" to document.getGenericCollection<Long>("sharedMessageIds"),
            "pollId" to document.getNullableLongValue("pollId"),
            "postId" to document.getNullableLongValue("postId"),
            "todoId" to document.getNullableLongValue("todoId"),
            "isVisible" to true,
            "isDeleted" to false,
            "isEmptyEntities" to false,
            "isPublic" to false,
        )

        when (contentType) {
            ContentType.TODO -> {
                val todo = document.getNestedObjectMap("content.todo")
                normalizeDocument["todoId"] = todo.getLongValue("todoId")
                normalizeDocument["todoRoomId"] = todo.getLongValue("roomId")
                normalizeDocument["todoStatus"] = todo.getStringValue("status")
                normalizeDocument["todoCreatorId"] = todo.getLongValue("creatorId")
                normalizeDocument["todoAssigneeIds"] = todo.getGenericList<Long>("assigneeIds")
                normalizeDocument["todoCompleterIds"] = todo.getGenericList<Long>("completerIds")
                normalizeDocument["todoSearchIds"] = extractTodoSearchIds(todo)
                normalizeDocument["todoProgress"] = todo.getLongValue("progress") ?: 0
                normalizeDocument["todoCreatedAt"] = todo.getLocalDateTimeUnwrappedValue("createdAt")
                normalizeDocument["todoUpdatedAt"] = todo.getLocalDateTimeUnwrappedValue("updatedAt")
                normalizeDocument["todoClosedAt"] = todo.getLocalDateTimeUnwrappedValue("closedAt")
                normalizeDocument["text"] = "$title\n$body".trim()
            }
            ContentType.POST -> {
                normalizeDocument["text"] = "$title\n$body".trim()
            }

            ContentType.POLL -> {
                normalizeDocument["pollItems"] = emptyList<Any>()
            }

            else -> {}
        }

        if (attachmentNames.isNotEmpty()) {
            var contentsPr = normalizeDocument.getStringValue("contents_pr")!!
            attachmentNames.forEach {
                contentsPr += " $it"
            }

            normalizeDocument["contents_pr"] = contentsPr
        }

        return normalizeDocument
    }

    private fun extractTodoSearchIds(todo: Map<String, Any?>) : List<Long> {
        val assigneeIds = todo.getGenericList<Long>("assigneeIds")
        val creatorId = todo.getLongValue("creatorId")

        assigneeIds.add(creatorId!!)
        return assigneeIds.distinct()
    }

    private fun extractMentions(document: Document) = objectMapper.writeValueAsString(document["mentions"])
    private fun extractAttachmentIds(document: Document) : List<Long> {
        val attachments = document.getGenericCollection<Map<String, Any?>>("attachments")
        return attachments.map { it.getLongValue("id")!! }
    }
    private fun extractAttachmentNames(document: Document) : List<String> {
        val attachments = document.getGenericCollection<Map<String, Any?>>("attachments")
        return attachments.map { it.getNestedStringValue("content.title")!! }
    }

    private fun extractTitle(contentType: ContentType, document: Document) : String {
        return if (contentType == ContentType.TODO) {
            document.getNestedNullableStringValue("content.todo.title")?.trim() ?: ""
        } else {
            document.getNestedNullableStringValue("content.title")?.trim() ?: ""
        }
    }

    private fun extractBody(contentType: ContentType, document: Document) : String {
        return if (contentType == ContentType.TODO) {
            document.getNestedNullableStringValue("content.todo.description")?.trim() ?: ""
        } else {
            document.getNestedNullableStringValue("content.body")?.trim() ?: ""
        }
    }
}
