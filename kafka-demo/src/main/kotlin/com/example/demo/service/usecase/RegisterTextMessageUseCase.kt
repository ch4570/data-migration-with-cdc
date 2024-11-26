package com.example.demo.service.usecase

import com.example.demo.event.dto.TextMessageEventPayload

interface RegisterTextMessageUseCase {

    fun registerTextMessage(textMessage: TextMessageEventPayload)
}
