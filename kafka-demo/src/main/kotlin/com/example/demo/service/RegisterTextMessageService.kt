package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.TextMessageEvent
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.repository.TextMessageEventRepository
import com.example.demo.service.usecase.RegisterTextMessageUseCase
import com.example.demo.util.markIsComplete
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class RegisterTextMessageService(
    private val mongoTemplate: MongoTemplate,
    private val textMessageEventRepository: TextMessageEventRepository,
) : RegisterTextMessageUseCase {

    override fun registerTextMessage(textMessage: TextMessageEventPayload) {
        if (textMessageEventRepository.existsByIdAndEventStatus(textMessage.id, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(textMessage.id, TextMessageEvent::class.java)
            // ES SEND

        } catch (e: Exception) {
            // rollback logic
        }


    }


}
