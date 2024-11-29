package com.example.demo.service

import com.example.demo.dto.RegisterMessageResponse
import com.example.demo.entity.Attachment
import com.example.demo.entity.MessageOutbox
import com.example.demo.entity.OperationType
import com.example.demo.repository.MessageEventRepository
import com.example.demo.service.request.RegisterMessageServiceRequest
import com.example.demo.service.usecase.RegisterMessageUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
@Transactional
class RegisterMessageService(
    private val messageEventRepository: MessageEventRepository,
) : RegisterMessageUseCase {

    private val logger = LoggerFactory.getLogger(RegisterMessageService::class.java)

    override fun registerMessage(request: RegisterMessageServiceRequest) : Mono<RegisterMessageResponse> {
        return try {
            val attachments = request.files.map {
                Attachment(
                    fileId = it.fileId,
                    fileName = it.fileName,
                    extension = it.extension,
                    operationType = OperationType.CREATE,
                )
            }
            val messageOutbox = MessageOutbox(
                senderId = request.senderId,
                content = request.content,
                roomId = request.roomId,
                operationType = OperationType.CREATE,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                attachments = attachments
            )

            messageEventRepository.save(messageOutbox)
                .map { RegisterMessageResponse(it.id!!) }

        } catch (e: Exception) {
            logger.error("에러가 났네")
            e.printStackTrace()
            throw e
        }
    }
}
