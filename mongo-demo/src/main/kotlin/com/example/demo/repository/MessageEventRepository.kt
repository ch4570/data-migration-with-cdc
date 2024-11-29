package com.example.demo.repository

import com.example.demo.entity.MessageOutbox
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageEventRepository : ReactiveMongoRepository<MessageOutbox, String>
