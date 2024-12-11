package com.example.demo.event.dlt

import com.example.demo.entity.ContentType
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.service.usecase.RegisterTextMessageUseCase
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TextMessageDLTEventListener(
    private val registerTextMessageUseCase: RegisterTextMessageUseCase,
    private val objectMapper: ObjectMapper,
) {

    private val logger = LoggerFactory.getLogger(TextMessageDLTEventListener::class.java)

//    @Async
//    @KafkaListener(topics = ["text-message-event.DLT"], groupId = "consumer-group", containerFactory = "text-message-event-dlt-consumer")
//    fun handleEvent(record: ConsumerRecord<String, TextMessageEventPayload>, acknowledgment: Acknowledgment) {
//        logger.info("이벤트 수신 완료 = [${record.value()}], 수신 시각 = [${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}]")
//        registerTextMessageUseCase.registerTextMessage(record.value())
//        acknowledgment.acknowledge()
//    }

    @Async
    @KafkaListener(topics = ["message-outbox-toss"], groupId = "consumer-group", containerFactory = "message-outbox-toss-consumer")
    fun handleEvent(records: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        logger.info("이벤트 수신 완료 = [${records.value()}]")
        val node = objectMapper.readTree(records.value())
            .get("payload").get("after").textValue()

        val value = objectMapper.readTree(node)
        val contentType = ContentType.valueOf(value.get("contentType").textValue().uppercase())

        when (contentType) {
            ContentType.TEXT -> {

            }

            ContentType.FILE -> {

            }

            else -> {

            }
        }


        logger.info("contentType = [${value.get("contentType").textValue()}]")
        acknowledgment.acknowledge()
    }
}
