package ru.omsu.eservice.domain.interactor

import ru.omsu.eservice.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun login(login: String, password: String) = loginRepository.login(login, password)
}