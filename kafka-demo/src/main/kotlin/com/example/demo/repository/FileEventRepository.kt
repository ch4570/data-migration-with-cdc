package com.example.demo.repository

import com.example.demo.entity.EventStatus
import com.example.demo.entity.FileEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface FileEventRepository : MongoRepository<FileEvent, String> {

    fun existsByIdAndEventStatus(fileId: String, eventStatus: EventStatus) : Boolean
}
