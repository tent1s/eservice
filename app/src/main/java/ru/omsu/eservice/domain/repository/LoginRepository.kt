package ru.omsu.eservice.domain.repository

import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason

interface LoginRepository {
    suspend fun login(login: String, password: String): Either<ErrorReason, Unit>
}