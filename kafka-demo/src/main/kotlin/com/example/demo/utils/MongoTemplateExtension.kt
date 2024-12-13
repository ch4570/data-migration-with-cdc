package com.example.demo.utils

import com.example.demo.entity.EventStatus
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update


fun MongoTemplate.markIsComplete(id: ObjectId, document: String) {
    val query = Query(Criteria.where("_id").`is`(id))
    val update = Update().set("eventStatus", EventStatus.COMPLETE)

    updateFirst(query, update, document)
}

fun MongoTemplate.findMessageEventIsNotCompleted(limit: Int, document: String) : List<Document> {
    val query = Query()
        .addCriteria(Criteria.where("eventStatus").`is`(EventStatus.CREATED))
        .limit(limit)

    return find(query, Document::class.java, document)
}
