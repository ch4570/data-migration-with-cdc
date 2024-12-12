package com.example.demo.entity

enum class EventStatus {
    CREATED,
    COMPLETE,
}

enum class OperationType {
    CREATE,
    UPDATE,
    DELETE
}

enum class ContentType {
    TEXT,
    COMMENT,
    TODO,
    POLL,
    CONNECT,
    POST,
}
