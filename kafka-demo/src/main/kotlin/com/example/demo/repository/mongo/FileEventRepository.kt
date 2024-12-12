package com.example.demo.repository.mongo

import com.example.demo.entity.EventStatus
import com.example.demo.entity.mongo.FileEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface FileEventRepository : MongoRepository<FileEvent, String> {

}
