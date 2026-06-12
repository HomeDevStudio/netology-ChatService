package ru.netology.chat

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.exception.MessageNotFoundException
import ru.netology.exception.StringIsEmptyException

class MessageServiceTest {
    val messageService = MessageService
    val chatService = ChatService

    @Before
    fun before() {
        messageService.clear()
        chatService.clear()
    }

    @Test
    fun createWithoutChat() {
        val id = messageService.create(
            Message(
                chatId = 0,
                text = "text",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.READ
            )
        )
        assertEquals(1, id)
        val size = messageService.size()
        assertEquals(1, size)
    }

    @Test
    fun createWithChat() {
        chatService.create(Chat(userId = 1, otherUserId = 2))
        val id = messageService.create(
            Message(
                chatId = 1, text = "text",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.READ
            )
        )
        assertEquals(1, id)
        val size = messageService.size()
        assertEquals(1, size)
    }

    @Test
    fun getById() {
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
                readStatus = ReadStatus.READ
            )
        )
        val message = messageService.getById(2)
        assertEquals(2, message.id)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getByIdWithException() {
        messageService.getById(2)
    }

    @Test
    fun getAll() {
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
                readStatus = ReadStatus.READ
            )
        )
        val size = messageService.getAll().size
        assertEquals(2, size)
    }

    @Test
    fun update() {
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
                readStatus = ReadStatus.READ
            )
        )
        val code = messageService.update(messageService.getById(2).copy(text = "text2_update"))
        assertEquals(1, code)
        val message = messageService.getById(2)
        assertEquals("text2_update", message.text)
    }

    @Test(expected = StringIsEmptyException::class)
    fun updateWithTextStringIsEmptyException() {
        messageService.create(
            Message(
                chatId = 1,
                text = "text1",
                userId = 1,
                otherUserId = 2,
                readStatus = ReadStatus.READ
            )
        )
        messageService.update(messageService.getById(1).copy(text = ""))
    }

    @Test
    fun delete() {
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
                readStatus = ReadStatus.READ
            )
        )
        val code = messageService.delete(1)
        assertEquals(1, code)
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteWithException() {
        messageService.delete(1)
    }
}