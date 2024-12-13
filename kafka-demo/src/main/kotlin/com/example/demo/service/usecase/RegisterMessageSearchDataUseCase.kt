package com.example.demo.service.usecase

import org.bson.Document


interface RegisterMessageSearchDataUseCase {

    fun saveMessageData(document: Document)
}
