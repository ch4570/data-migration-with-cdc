package com.example.demo.service

import com.example.demo.entity.ContentType
import com.example.demo.service.usecase.RegisterMessageSearchDataUseCase
import com.example.demo.utils.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.util.Date

@Service
@Transactional
@Suppress("UNCHECKED_CAST")
class RegisterMessageSearchDataService(
    private val mongoTemplate: MongoTemplate,
    private val objectMapper: ObjectMapper,
) : RegisterMessageSearchDataUseCase {
    override fun saveMessageData(document: Document) {
        normalizeDocument(document)
        mongoTemplate.markIsComplete(document["_id"] as ObjectId, "message-event")
    }

    private fun normalizeDocument(document: Document): Document {
        val contentType = ContentType.valueOf(document.getString("contentType"))
        val content = document.getObjectMap("content")
        val todo = document.getObjectMap("todo")

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

        val normalizeDocument = mutableMapOf(
            "id" to document.getNullableLongValue("id"),
            "permission" to document.getNullableIntValue("permission"),
            "teamId" to document.getNullableLongValue("teamId"),
            "writerId" to document.getNullableLongValue("writerId"),
            "feedbackId" to document.getNullableLongValue("feedbackId"),
            "shareEntities" to document["shareEntities"] as List<Long>,
            "contentType" to contentType,
            "contents" to contents,
            "contents_pr" to contents,
            "mentionString" to mentions,
            "commentCount" to document.getNullableIntValue("commentCount"),
            "createdAt" to document.getLocalDateTimeValue("createdAt"),
            "updatedAt" to document.getLocalDateTimeValue("updatedAt"),
            "from" to document.getString("from"),
            "isThreaded" to document["isThreaded"] as Boolean,
            "isFormatted" to document["isFormatted"] as Boolean,
            "connectType" to content["connectType"] as String?,
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

        println("normalizedocument = $normalizeDocument")
        return document
    }

    private fun extractMentions(document: Document) = objectMapper.writeValueAsString(document["mentions"])
    private fun extractAttachmentIds(document: Document) : List<Long> {
        val attachments = document["attachments"] as List<Map<String, Any?>>
        return attachments.map { it["id"] as Long }
    }
    private fun extractAttachmentNames(document: Document) : List<String> {
        val attachments = document["attachments"] as List<Map<String, Any?>>
        return attachments.map { (it["content"] as Map<String, Any?>)["title"] as String }
    }

    private fun extractTitle(contentType: ContentType, document: Document) : String {
        println("content = [${(document["content"] as Map<String, Any?>)}]")
        return if (contentType == ContentType.TODO) {
            val todo = (document["content"] as Map<String, Any?>)["todo"] as Map<String, Any?>
            todo["title"] as String
        } else {
            val title = ((document["content"] as Map<String, Any?>)["title"] as String?)
            if (title.isNullOrBlank()) "" else title.trim()
        }
    }

    private fun extractBody(contentType: ContentType, document: Document) : String {
        return if (contentType == ContentType.TODO) {
            val todo = (document["content"] as Map<String, Any?>)["todo"] as Map<String, Any?>
            todo["description"] as String
        } else {
            ((document["content"] as Map<String, Any?>)["body"] as String).trim()
        }
    }
}
