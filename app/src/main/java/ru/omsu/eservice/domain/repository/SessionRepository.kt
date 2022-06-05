package ru.omsu.eservice.domain.repository

interface SessionRepository {
    fun hasSession() : Boolean

    fun clear()

    fun newSessionBeCreated()
}