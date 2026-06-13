package ru.netology.chat

import ru.netology.exception.ChatNotFoundException

object ChatService {
    private var chats = mutableListOf<Chat>()
    private var chatId = 0

    fun create(entity: Chat): Int {
        chats.add(entity.copy(id = ++chatId))
        return chats.last().id
    }

    fun getById(id: Int): Chat {
        var res: Chat? = null

        for (note in chats) {
            if (note.id == id) {
                res = note
            }
        }
        return res ?: throw ChatNotFoundException("chat not found with id $id")
    }

    // Получить список чатов
    fun getChats(): List<Chat> {
        return chats
    }

    fun getChatMessages(chatId: Int): List<Message> {
        val res = MessageService.getByChatId(chatId)
        if (res.isEmpty()) {
            println("Нет сообщений")
        }
        return res
    }

    fun getUnreadChatsCount(): Int {
        return chats.filter { getChatMessages(it.id).any { it.readStatus == ReadStatus.UNREAD } }.size
    }

    fun delete(id: Int): Int {
        chats.remove(getById(id))
        return 1
    }

    fun deleteMessage(messageId: Int): Int {
        return MessageService.delete(messageId)
    }

    fun clear() {
        chats.clear()
        chatId = 0
    }

    fun size(): Int {
        return chats.size
    }
}