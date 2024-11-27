package com.example.demo.service

import com.example.demo.entity.EventStatus
import com.example.demo.entity.FileEvent
import com.example.demo.event.dto.FileEventPayload
import com.example.demo.repository.FileEventRepository
import com.example.demo.service.usecase.RegisterFileEventUseCase
import com.example.demo.util.markIsComplete
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterFileEventService(
    private val mongoTemplate: MongoTemplate,
    private val fileEventRepository: FileEventRepository,
) : RegisterFileEventUseCase {

    override fun registerFileEvent(payload: FileEventPayload) {
        if (fileEventRepository.existsByIdAndEventStatus(payload.id, EventStatus.COMPLETE)) {
            return
        }

        try {
            mongoTemplate.markIsComplete(payload.fileId, FileEvent::class.java)
            // ES Send
        } catch (e: Exception) {
            // rollback
        }
    }
}
