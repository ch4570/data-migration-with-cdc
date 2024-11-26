package com.example.demo.dto

import com.example.demo.service.request.RegisterMessageServiceRequest

data class RegisterMessageCommand(
    private val senderId: Long,
    private val content: String,
    private val roomId: Long,
    private val files: List<FileCommand> = emptyList(),
) {

    fun toServiceRequest() = RegisterMessageServiceRequest(
        senderId = senderId,
        content = content,
        roomId = roomId,
        files = files,
    )
}

data class FileCommand(
    val fileId: String,
    val fileName: String,
    val extension: String,
)
