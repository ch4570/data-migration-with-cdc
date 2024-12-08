package com.example.demo.event.dlt

import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.service.usecase.RegisterFileEventUseCase
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class SingleFileDLTEventListener(
    private val registerFileEventUseCase: RegisterFileEventUseCase,
) {

    private val logger = LoggerFactory.getLogger(SingleFileDLTEventListener::class.java)

    @Async
    @KafkaListener(topics = ["file-event.DLT"], groupId = "consumer-group", containerFactory = "single-file-event-dlt-consumer")
    fun handleEvent(record: ConsumerRecord<String, SingleFileEventPayload>, acknowledgment: Acknowledgment) {
        logger.info("이벤트 수신 완료 = [${record.value()}], 수신 시각 = [${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}]")
        registerFileEventUseCase.registerFileEvent(record.value())
        acknowledgment.acknowledge()
    }
}
