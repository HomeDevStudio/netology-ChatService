package ru.netology.chat

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.exception.ChatNotFoundException

class ChatServiceTest {
    val chatService = ChatService
    val messageService = MessageService

    @Before
    fun before() {
        chatService.clear()
        messageService.clear()
    }

    @Test
    fun create() {
        val id = chatService.create(Chat(userId = 1, otherUserId = 2))
        assertEquals(1, id)
        val size = chatService.size()
        assertEquals(1, size)
    }

    @Test
    fun getById() {
        chatService.create(Chat(userId = 1, otherUserId = 2))
        chatService.create(Chat(userId = 2, otherUserId = 3))
        val chat = chatService.getById(2)
        assertEquals(2, chat.id)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getByIdWithException() {
        chatService.getById(2)
    }

    @Test
    fun getChats() {
        chatService.create(Chat(userId = 1, otherUserId = 2))
        chatService.create(Chat(userId = 2, otherUserId = 3))
        val size = chatService.getChats().size
        assertEquals(2, size)
    }

    @Test
    fun getChatMessages() {
        val chatId = chatService.create(Chat(userId = 1, otherUserId = 2))
        messageService.create(
            Message(
                chatId = 1,
                text = "text1",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.READ
            )
        )
        messageService.create(
            Message(
                chatId = 1,
                text = "text2",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.UNREAD
            )
        )
        val messages = chatService.getChatMessages(chatId)
        assertEquals(2, messages.size)
    }

    @Test
    fun getUnreadChatsCount() {
        chatService.create(Chat(userId = 1, otherUserId = 2))
        messageService.create(
            Message(
                chatId = 1,
                text = "text1",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.READ
            )
        )
        messageService.create(
            Message(
                chatId = 1,
                text = "text2",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.UNREAD
            )
        )

        chatService.create(Chat(userId = 2, otherUserId = 3))
        messageService.create(
            Message(
                chatId = 2,
                text = "text1",
                userId = 2,
                otherUserId = 3,
                readStatus = ReadStatus.READ
            )
        )

        val result = chatService.getUnreadChatsCount()
        assertEquals(1, result)
    }

    @Test
    fun delete() {
        chatService.create(Chat(userId = 1, otherUserId = 2))
        chatService.create(Chat(userId = 2, otherUserId = 3))
        val code = chatService.delete(1)
        assertEquals(1, code)
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteWithException() {
        chatService.delete(1)
    }
}