package com.example.demo.event.dlt

import com.example.demo.service.usecase.MessageEventPreProcessor
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class MessageOutboxEventListener(
    private val messageEventPreProcessor: MessageEventPreProcessor,
) {

    private val logger = LoggerFactory.getLogger(MessageOutboxEventListener::class.java)

    @Async
    @KafkaListener(topics = ["message-outbox-toss"], groupId = "consumer-group", containerFactory = "message-outbox-toss-consumer")
    fun handleEvent(records: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        try {
            messageEventPreProcessor.preProcess(records.value())
        } catch (e : Exception) {
            logger.error("메시지 처리 중 에러 발생 = [$e], 메시지 = [${records.value()}]")
            logger.error("DLT 전송...")
        } finally {
            acknowledgment.acknowledge()
        }
    }
}
