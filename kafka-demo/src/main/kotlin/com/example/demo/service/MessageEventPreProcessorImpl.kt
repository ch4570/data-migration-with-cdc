package com.example.demo.service

import com.example.demo.entity.OperationType
import com.example.demo.event.dto.Attachment
import com.example.demo.repository.mongo.FileEventRepository
import com.example.demo.service.usecase.MessageEventPreProcessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class MessageEventPreProcessorImpl(
    private val objectMapper: ObjectMapper,
    private val fileEventRepository: FileEventRepository,
) : MessageEventPreProcessor {

    override fun preProcess(messageBody: String) {
        // payload 추출
        val stringifyPayload = objectMapper.readTree(messageBody).get("payload").textValue()

        // payload 변환
        val convertedPayload = objectMapper.readTree(stringifyPayload)
        // 첨부파일 변환
        val attachment = objectMapper.convertValue(convertedPayload.get("attachments"), Array<Attachment>::class.java)

        // operation Type 추출 -> Create, Update, Delete
        val operationType = OperationType.valueOf(convertedPayload.get("operationType").textValue().uppercase())

        if (attachment.isNotEmpty()) {
            val fileEvents = attachment.map {
                it.convertToFileEvent(operationType)
            }

            fileEventRepository.saveAll(fileEvents)
        }



    }
}
