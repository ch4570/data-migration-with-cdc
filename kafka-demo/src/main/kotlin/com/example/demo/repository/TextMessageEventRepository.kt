package com.example.demo.repository

import com.example.demo.entity.EventStatus
import com.example.demo.entity.TextMessageEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface TextMessageEventRepository : MongoRepository<TextMessageEvent, String> {

    fun existsByIdAndEventStatus(id: String, eventStatus: EventStatus) : Boolean
}
