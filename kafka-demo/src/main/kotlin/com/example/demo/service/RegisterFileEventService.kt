package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.elastic.SingleFileElastic
import com.example.demo.entity.mongo.FileEvent
import com.example.demo.event.dto.SingleFileEventPayload
import com.example.demo.repository.elastic.SingleFileElasticRepository
import com.example.demo.repository.mongo.FileEventRepository
import com.example.demo.service.usecase.RegisterFileEventUseCase
import com.example.demo.utils.markIsComplete
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class RegisterFileEventService(
    private val mongoTemplate: MongoTemplate,
    private val fileEventRepository: FileEventRepository,
    private val fileElasticRepository: SingleFileElasticRepository,
) : RegisterFileEventUseCase {

    private val logger = LoggerFactory.getLogger(RegisterFileEventService::class.java)

    override fun registerFileEvent(payload: SingleFileEventPayload) {
        if (fileEventRepository.existsByIdAndEventStatus(payload.id.value, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(payload.id.value, FileEvent::class.java)
            // ES Send
            val fileElastic = SingleFileElastic(
                fileId = payload.fileId,
                fileName = payload.fileName,
                extension = payload.extension,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )

            fileElasticRepository.save(fileElastic)

        } catch (e: Exception) {
            logger.error("Error : [${e.message}], eventDetail = [$payload]")
            throw e
        }
    }
}
