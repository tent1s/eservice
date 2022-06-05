package ru.omsu.eservice.domain.repository

interface SessionRepository {
    fun hasSession() : Boolean

    suspend fun clear()

    fun newSessionBeCreated()
}