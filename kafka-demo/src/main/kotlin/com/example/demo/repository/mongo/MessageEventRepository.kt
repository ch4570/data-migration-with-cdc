package com.example.demo.repository.mongo

import com.example.demo.entity.EventStatus
import com.example.demo.entity.mongo.MessageEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageEventRepository : MongoRepository<MessageEvent, String> {

    fun existsByIdAndEventStatus(messageId: String, eventStatus: EventStatus) : Boolean
}
