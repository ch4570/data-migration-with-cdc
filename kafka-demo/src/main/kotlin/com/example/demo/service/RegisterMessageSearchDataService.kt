package com.example.demo.service

import com.example.demo.service.usecase.RegisterMessageSearchDataUseCase
import com.example.demo.utils.markIsComplete
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterMessageSearchDataService(
    private val mongoTemplate: MongoTemplate,
) : RegisterMessageSearchDataUseCase {
    override fun saveMessageData(document: Document) {
        mongoTemplate.markIsComplete(document["_id"] as ObjectId, "message-event")
    }
}
