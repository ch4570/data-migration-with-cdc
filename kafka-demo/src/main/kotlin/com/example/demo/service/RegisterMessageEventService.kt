package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.mongo.FileEvent
import com.example.demo.entity.mongo.MessageEvent
import com.example.demo.entity.mongo.TextMessageEvent
import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.repository.mongo.FileEventRepository
import com.example.demo.repository.mongo.MessageEventRepository
import com.example.demo.repository.mongo.TextMessageEventRepository
import com.example.demo.service.usecase.RegisterMessageEventUseCase
import com.example.demo.utils.markIsComplete
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterMessageEventService(
    private val mongoTemplate: MongoTemplate,
    private val fileEventRepository: FileEventRepository,
    private val messageEventRepository: MessageEventRepository,
    private val textMessageEventRepository: TextMessageEventRepository,
) : RegisterMessageEventUseCase {

    private val logger = LoggerFactory.getLogger(RegisterMessageEventService::class.java)

    override fun registerMessageEvent(messageEvent: MessageEventPayload) {
        if (messageEventRepository.existsByIdAndEventStatus(messageEvent.id.value, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(messageEvent.id.value, MessageEvent::class.java)

            val textMessageEvent = TextMessageEvent(
                messageId = messageEvent.id.value,
                senderId = messageEvent.senderId.value,
                content = messageEvent.content,
                roomId = messageEvent.roomId.value,
                operationType = messageEvent.operationType,
                eventStatus = EventStatus.CREATED,
            )

            textMessageEventRepository.save(textMessageEvent)

            if (messageEvent.hasFiles()) {
                val attachments = messageEvent.attachments.map {
                    FileEvent(
                        fileId = it.fileId,
                        fileName = it.fileName,
                        extension = it.extension,
                        operationType = it.operationType,
                        eventStatus = EventStatus.CREATED,
                    )
                }

                fileEventRepository.saveAll(attachments)
            }
        } catch (e: Exception) {
            logger.error("Error : [${e.message}], eventDetail = [$messageEvent]")
            throw e
        }
    }
}
