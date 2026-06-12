package ru.netology.chat

import ru.netology.CrudService
import ru.netology.exception.ChatNotFoundException
import ru.netology.exception.MessageNotFoundException
import ru.netology.exception.StringIsEmptyException

object MessageService : CrudService<Message> {
    private var messages = mutableListOf<Message>()
    private var messageId = 0
    private val chatService = ChatService

    override fun create(entity: Message): Int {
        val chatId = try {
            chatService.getById(entity.chatId).id // проверка наличия чата
        } catch (e: ChatNotFoundException) {
            chatService.create(Chat(userId = entity.userId, otherUserId = entity.otherUserId))
        }
        messages.add(entity.copy(id = ++messageId))
        return messages.last().id
    }

    override fun getById(id: Int): Message {
        var res: Message? = null

        for (message in messages) {
            if (message.id == id) {
                res = message
            }
        }
        return res ?: throw MessageNotFoundException("message not found with id $id")
    }

    fun getByChatId(chatId: Int): List<Message> {
        return messages.filter { message: Message -> message.chatId == chatId }
    }

    override fun getAll(): List<Message> {
        return messages
    }

    override fun update(entity: Message): Int {
        val chat = chatService.getById(entity.chatId) // проверка наличия чата
        val message = getById(entity.id)
        if (entity.text.isEmpty()) throw StringIsEmptyException("text is empty")
        message.text = entity.text
        return 1
    }

    override fun delete(id: Int): Int {
        messages.remove(getById(id))
        return 1
    }

    fun clear() {
        messages.clear()
        messageId = 0
    }

    fun size(): Int {
        return messages.size
    }
}