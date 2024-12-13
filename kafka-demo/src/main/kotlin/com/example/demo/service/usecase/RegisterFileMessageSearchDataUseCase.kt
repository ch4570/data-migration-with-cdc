package com.example.demo.service.usecase

import org.bson.Document

interface RegisterFileMessageSearchDataUseCase {

    fun saveFileData(document: Document)
}
