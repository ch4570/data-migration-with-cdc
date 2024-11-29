package com.example.demo.service.usecase

import com.example.demo.event.dto.MessageOutboxPayload

interface RegisterFullMessageUseCase {

    fun registerFullMessage(payload: MessageOutboxPayload)
}
