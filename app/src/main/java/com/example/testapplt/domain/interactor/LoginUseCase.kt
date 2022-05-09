package com.example.testapplt.domain.interactor

import com.example.testapplt.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun login(login: String, password: String) = loginRepository.login(login, password)
}