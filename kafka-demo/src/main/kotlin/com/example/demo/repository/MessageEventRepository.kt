package com.example.demo.repository

import com.example.demo.entity.EventStatus
import com.example.demo.entity.MessageEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageEventRepository : MongoRepository<MessageEvent, String> {

    fun existsByIdAndEventStatus(messageId: String, eventStatus: EventStatus) : Boolean
}
