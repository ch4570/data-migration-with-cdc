package com.example.demo.service.usecase

import com.example.demo.event.dto.SingleFileEventPayload

interface RegisterFileEventUseCase {

    fun registerFileEvent(payload: SingleFileEventPayload)
}
