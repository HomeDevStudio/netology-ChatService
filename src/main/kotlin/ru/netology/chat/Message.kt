package ru.netology.chat

enum class ReadStatus { READ, UNREAD }

data class Message(
    val id: Int = 0,
    val chatId: Int,
    var text: String,
    val userId: Int,
    val otherUserId: Int,
    val readStatus: ReadStatus
)
