package com.example.demo.service.usecase

interface MessageEventPreProcessor {

    fun preProcess(messageBody: String)
}
