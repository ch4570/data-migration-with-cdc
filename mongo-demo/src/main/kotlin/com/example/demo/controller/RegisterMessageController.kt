package com.example.demo.controller

import com.example.demo.dto.BaseResponse
import com.example.demo.dto.RegisterMessageCommand
import com.example.demo.service.usecase.RegisterMessageUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterMessageController(
    private val registerMessageUseCase: RegisterMessageUseCase,
) {

    @PostMapping("/api/v1/messages")
    fun registerMessage(@RequestBody command: RegisterMessageCommand) =
        registerMessageUseCase.registerMessage(command.toServiceRequest())
            .map { BaseResponse.ok(it) }
}
