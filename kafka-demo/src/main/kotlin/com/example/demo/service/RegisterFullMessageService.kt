package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.mongo.MessageEvent
import com.example.demo.event.dto.Attachment
import com.example.demo.event.dto.MessageOutboxPayload
import com.example.demo.repository.mongo.MessageEventRepository
import com.example.demo.service.usecase.RegisterFullMessageUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class RegisterFullMessageService(
    private val messageEventRepository: MessageEventRepository,
) : RegisterFullMessageUseCase {

    override fun registerFullMessage(payload: MessageOutboxPayload) {
        val attachments = payload.attachments.map {
            Attachment(
                fileId = it.fileId,
                fileName = it.fileName,
                extension = it.extension,
                operationType = it.operationType,
            )
        }

        val messageEvent = MessageEvent(
            senderId = payload.senderId.value,
            content = payload.content,
            roomId = payload.roomId.value,
            operationType = payload.operationType,
            eventStatus = EventStatus.CREATED,
            attachments = attachments,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        messageEventRepository.save(messageEvent)
    }
}
