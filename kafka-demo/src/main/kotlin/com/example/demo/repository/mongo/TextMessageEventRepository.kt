package com.example.demo.repository.mongo

import com.example.demo.entity.EventStatus
import com.example.demo.entity.mongo.TextMessageEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface TextMessageEventRepository : MongoRepository<TextMessageEvent, String> {

    fun existsByIdAndEventStatus(id: String, eventStatus: EventStatus) : Boolean
}
