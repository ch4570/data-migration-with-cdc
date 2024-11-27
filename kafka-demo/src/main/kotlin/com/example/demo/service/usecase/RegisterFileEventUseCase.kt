package com.example.demo.service.usecase

import com.example.demo.event.dto.FileEventPayload

interface RegisterFileEventUseCase {

    fun registerFileEvent(payload: FileEventPayload)
}
