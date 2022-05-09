package com.example.testapplt.data.remote.login.repository

import com.example.testapplt.domain.repository.LoginRepository
import com.example.testapplt.utils.Either
import com.example.testapplt.utils.ErrorReason
import com.example.testapplt.utils.map
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val EServiceApi: EServiceApi
) : LoginRepository {

    private suspend fun getCsrfToken(): Either<ErrorReason, String> =
        EServiceApi.getCsrfToken().map {
            it.csrf
        }

    override suspend fun login(login: String, password: String): Either<ErrorReason, Unit> {
        val csrf = getCsrfToken()
        return if (csrf is Either.Success) {
            EServiceApi.login("$login@eservice", password, csrf.data)
            EServiceApi.loginBackend()
        } else {
            csrf.map {}
        }
    }

}