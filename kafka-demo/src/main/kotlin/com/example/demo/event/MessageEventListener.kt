package com.example.demo.event

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.service.usecase.RegisterMessageEventUseCase
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class MessageEventListener(
    private val registerMessageUseCase: RegisterMessageEventUseCase,
) {

    @KafkaListener(topics = ["message-event"], groupId = "group-example-1", containerFactory = "message-event-consumer")
    fun handleEvent(record: ConsumerRecord<String, MessageEventPayload>, acknowledgment: Acknowledgment) {
        registerMessageUseCase.registerMessageEvent(record.value())
        acknowledgment.acknowledge()
    }
}
