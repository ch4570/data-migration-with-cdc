package com.example.demo.util

import com.example.demo.entity.EventStatus
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update


fun MongoTemplate.markIsComplete(id: String, clazz: Class<*>) {
    val query = Query(Criteria.where("id").`is`(id))
    val update = Update().set("eventStatus", EventStatus.COMPLETE)

    updateFirst(query, update, clazz)
}
