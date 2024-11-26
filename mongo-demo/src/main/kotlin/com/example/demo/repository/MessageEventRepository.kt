package com.example.demo.repository

import com.example.demo.entity.MessageEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageEventRepository : ReactiveMongoRepository<MessageEvent, String>
