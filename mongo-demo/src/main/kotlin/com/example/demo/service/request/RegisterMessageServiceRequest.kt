package com.example.demo.service.request

import com.example.demo.dto.FileCommand

data class RegisterMessageServiceRequest(
    val senderId: Long,
    val content: String,
    val roomId: Long,
    val files: List<FileCommand>,
) {
    fun hasFiles() = files.isNotEmpty()
}
