package ru.omsu.eservice.data.remote.login.repository

import ru.omsu.eservice.domain.repository.LoginRepository
import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason
import ru.omsu.eservice.utils.map
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
        } else {
            csrf.map {}
        }
    }

}