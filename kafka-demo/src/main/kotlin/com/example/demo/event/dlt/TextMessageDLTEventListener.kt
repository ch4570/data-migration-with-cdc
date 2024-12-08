package com.example.demo.event.dlt

import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.service.usecase.RegisterTextMessageUseCase
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
) {

    private val logger = LoggerFactory.getLogger(TextMessageDLTEventListener::class.java)

    @Async
    @KafkaListener(topics = ["text-message-event.DLT"], groupId = "consumer-group", containerFactory = "text-message-event-dlt-consumer")
    fun handleEvent(record: ConsumerRecord<String, TextMessageEventPayload>, acknowledgment: Acknowledgment) {
        logger.info("이벤트 수신 완료 = [${record.value()}], 수신 시각 = [${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}]")
        registerTextMessageUseCase.registerTextMessage(record.value())
        acknowledgment.acknowledge()
    }
}
