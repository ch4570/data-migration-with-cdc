package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.FileEvent
import com.example.demo.entity.MessageEvent
import com.example.demo.entity.TextMessageEvent
import com.example.demo.event.dto.MessageEventPayload
import com.example.demo.repository.FileEventRepository
import com.example.demo.repository.MessageEventRepository
import com.example.demo.repository.TextMessageEventRepository
import com.example.demo.service.usecase.RegisterMessageEventUseCase
import com.example.demo.util.markIsComplete
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

    override fun registerMessageEvent(messageEvent: MessageEventPayload) {
        if (messageEventRepository.existsByIdAndEventStatus(messageEvent.id, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(messageEvent.id, MessageEvent::class.java)

            val textMessageEvent = TextMessageEvent(
                messageId = messageEvent.id,
                senderId = messageEvent.senderId,
                content = messageEvent.content,
                roomId = messageEvent.roomId,
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
            // rollback
            throw e
        }
    }
}
