package com.example.demo.service.usecase

import com.example.demo.dto.RegisterMessageResponse
import com.example.demo.service.request.RegisterMessageServiceRequest
import reactor.core.publisher.Mono

interface RegisterMessageUseCase {

    fun registerMessage(request: RegisterMessageServiceRequest) : Mono<RegisterMessageResponse>
}
