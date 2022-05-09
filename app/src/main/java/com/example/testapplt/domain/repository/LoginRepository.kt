package com.example.testapplt.domain.repository

import com.example.testapplt.utils.Either
import com.example.testapplt.utils.ErrorReason

interface LoginRepository {
    suspend fun login(login: String, password: String): Either<ErrorReason, Unit>
}