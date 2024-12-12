package com.example.demo.service.usecase

import com.example.demo.event.dto.Attachment

interface MessageEventPreProcessor {

    fun preProcess(messageBody: String)
}
