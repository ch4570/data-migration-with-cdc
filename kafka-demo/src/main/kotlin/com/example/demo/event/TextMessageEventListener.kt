package com.example.demo.event

import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.service.usecase.RegisterMessageEventUseCase
import com.example.demo.service.usecase.RegisterTextMessageUseCase
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TextMessageEventListener(
    private val registerTextMessageUseCase: RegisterTextMessageUseCase,
) {

    private val logger = LoggerFactory.getLogger(TextMessageEventListener::class.java)

    @KafkaListener(topics = ["text-message-event"], groupId = "group-example-1", containerFactory = "text-message-event-consumer")
    fun handleEvent(record: ConsumerRecord<String, TextMessageEventPayload>, acknowledgment: Acknowledgment) {
        logger.info("이벤트 수신 완료 = [${record.value()}], 수신 시각 = [${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}]")
        registerTextMessageUseCase.registerTextMessage(record.value())
        acknowledgment.acknowledge()
    }
}
