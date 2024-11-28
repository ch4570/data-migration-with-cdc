package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.elastic.TextMessageElastic
import com.example.demo.entity.mongo.TextMessageEvent
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.repository.elastic.TextMessageElasticRepository
import com.example.demo.repository.mongo.TextMessageEventRepository
import com.example.demo.service.usecase.RegisterTextMessageUseCase
import com.example.demo.utils.markIsComplete
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterTextMessageService(
    private val mongoTemplate: MongoTemplate,
    private val textMessageEventRepository: TextMessageEventRepository,
    private val textMessageElasticRepository: TextMessageElasticRepository,
) : RegisterTextMessageUseCase {

    override fun registerTextMessage(textMessage: TextMessageEventPayload) {
        if (textMessageEventRepository.existsByIdAndEventStatus(textMessage.id.value, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(textMessage.id.value, TextMessageEvent::class.java)
            // ES SEND
            val textMessageElastic = TextMessageElastic(
                messageId = textMessage.messageId,
                senderId = textMessage.senderId.value,
                content = textMessage.content,
                roomId = textMessage.roomId.value,
                createdAt = textMessage.createdAt.value,
                updatedAt = textMessage.updatedAt.value,
            )

            textMessageElasticRepository.save(textMessageElastic)

        } catch (e: Exception) {
            // rollback logic
            throw e
        }


    }


}
