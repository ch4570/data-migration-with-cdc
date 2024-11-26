package com.example.demo.service.usecase

import com.example.demo.event.dto.MessageEventPayload

interface RegisterMessageEventUseCase {

    fun registerMessageEvent(messageEvent: MessageEventPayload)
}
