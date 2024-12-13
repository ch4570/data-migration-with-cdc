package com.example.demo.service

import com.example.demo.entity.ContentType
import com.example.demo.entity.EventStatus
import com.example.demo.entity.OperationType
import com.example.demo.event.dto.Attachment
import com.example.demo.repository.mongo.FileEventRepository
import com.example.demo.service.usecase.MessageEventPreProcessor
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.ZoneId

@Component
@Transactional
@Suppress("UNCHECKED_CAST")
class MessageEventPreProcessorImpl(
    private val objectMapper: ObjectMapper,
    private val fileEventRepository: FileEventRepository,
    private val mongoTemplate: MongoTemplate,
) : MessageEventPreProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preProcess(messageBody: String) {
        logger.info("Message 처리 시작, Message = [$messageBody]")

        // payload 추출
        val stringifyPayload = objectMapper.readTree(messageBody).get("payload").textValue()

        // Map으로 데이터 변환
        val mapValue = objectMapper.readValue(stringifyPayload, object: TypeReference<MutableMap<String, Any?>>() {})

        // 첨부파일 변환
        val attachment = objectMapper.convertValue(mapValue["attachments"], object: TypeReference<List<Attachment>>() {})

        // operation Type 추출 -> Create, Update, Delete
        val operationType = OperationType.valueOf((mapValue["operation"] as String).uppercase())

        if (attachment.isNotEmpty()) {
            val fileEvents = attachment.map {
                it.convertToFileEvent(operationType)
            }

            fileEventRepository.saveAll(fileEvents)
        }

        transformToSimpleMessage(mapValue)
        mongoTemplate.save(mapValue, "message-event")
        logger.info("메시지 처리 완료, 최종 메시지 = [$mapValue]")
    }

    private fun transformToSimpleMessage(messageMap: MutableMap<String, Any?>) {
        // attachments 삭제
        messageMap.remove("attachments")

        // _id 추출
        val objectId = (messageMap["_id"] as Map<String, String>)["\$oid"]!!

        // $oid 삭제 및 _id(messageId) 삽입
        messageMap.remove("_id")
        messageMap["messageId"] = objectId

        // contentType 설정
        messageMap["contentType"] = ContentType.valueOf((messageMap["contentType"] as String).uppercase())
        // eventStatus 설정
        messageMap["eventStatus"] = EventStatus.CREATED

        // 날짜 필드 추출 및 재설정
        setUnboxedLocalDateTime(messageMap)
    }

    private fun setUnboxedLocalDateTime(messageMap: MutableMap<String, Any?>) {
        messageMap["createdAt"] = Instant.ofEpochMilli((messageMap["createdAt"] as Map<String, Long>)["\$date"]!!)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        messageMap["updatedAt"] = Instant.ofEpochMilli((messageMap["updatedAt"] as Map<String, Long>)["\$date"]!!)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}
