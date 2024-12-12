package com.example.demo.service

import com.example.demo.entity.OperationType
import com.example.demo.event.dto.Attachment
import com.example.demo.repository.mongo.FileEventRepository
import com.example.demo.service.usecase.MessageEventPreProcessor
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class MessageEventPreProcessorImpl(
    private val objectMapper: ObjectMapper,
    private val fileEventRepository: FileEventRepository,
) : MessageEventPreProcessor {

    @Suppress("UNCHECKED_CAST")
    override fun preProcess(messageBody: String) {
        // payload 추출
        val stringifyPayload = objectMapper.readTree(messageBody).get("payload").textValue()

        // Map으로 데이터 변환
        val mapValue = objectMapper.readValue(stringifyPayload, object: TypeReference<MutableMap<String, Any?>>() {})

        // 첨부파일 변환
        val attachment = objectMapper.convertValue(mapValue["attachments"], object: TypeReference<List<Attachment>>() {})
        attachment.forEach {
            println("attachment = $it")
        }

        println("_id = ${(mapValue["_id"] as Map<String, String>)["\$oid"]}")

        // operation Type 추출 -> Create, Update, Delete
        val operationType = OperationType.valueOf((mapValue["operation"] as String).uppercase())

        if (attachment.isNotEmpty()) {
            val fileEvents = attachment.map {
                it.convertToFileEvent(operationType)
            }

            fileEventRepository.saveAll(fileEvents)
        }

        mapValue.remove("attachments")
        println("mapValue = [$mapValue]")
    }
}
