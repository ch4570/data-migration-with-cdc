package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.elastic.TextMessageElastic
import com.example.demo.entity.mongo.TextMessageEvent
import com.example.demo.event.dto.TextMessageEventPayload
import com.example.demo.repository.elastic.TextMessageElasticRepository
import com.example.demo.repository.mongo.TextMessageEventRepository
import com.example.demo.service.usecase.RegisterTextMessageUseCase
import com.example.demo.utils.markIsComplete
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class RegisterTextMessageService(
    private val mongoTemplate: MongoTemplate,
    private val textMessageEventRepository: TextMessageEventRepository,
    private val textMessageElasticRepository: TextMessageElasticRepository,
) : RegisterTextMessageUseCase {

    private val logger = LoggerFactory.getLogger(RegisterTextMessageService::class.java)

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
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )

            textMessageElasticRepository.save(textMessageElastic)

        } catch (e: Exception) {
            logger.error("Error : [${e.message}], eventDetail = [$textMessage]")
            throw e
        }


    }


}
