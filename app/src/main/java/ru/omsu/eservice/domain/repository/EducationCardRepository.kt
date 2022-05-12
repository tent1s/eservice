package ru.omsu.eservice.domain.repository

import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason

interface EducationCardRepository {
    suspend fun getToken(): Either<ErrorReason, Unit>
}