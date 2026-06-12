package ru.netology

interface CrudService<E> {
    fun create(entity: E): Int
    fun getById(id: Int): E
    fun getAll(): List<E>
    fun update(entity: E): Int
    fun delete(id: Int): Int
}