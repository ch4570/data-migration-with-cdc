package com.example.demo.event

import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.service.usecase.RegisterMessageEventUseCase
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class TextMessageEventListener(
    private val registerMessageUseCase: RegisterMessageEventUseCase,
) {

    @KafkaListener(topics = ["text-message-event"], groupId = "group-example-1", containerFactory = "text-message-event-consumer")
    fun handleEvent(record: ConsumerRecord<String, TextMessageEventPayload>, acknowledgment: Acknowledgment) {

        acknowledgment.acknowledge()
    }
}
