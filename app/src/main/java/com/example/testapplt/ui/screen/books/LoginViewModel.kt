package com.example.testapplt.ui.screen.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplt.domain.interactor.LoginUseCase
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val router: Router
) : ViewModel() {

    fun login(login: String, password: String) {
        viewModelScope.launch {
            loginUseCase.login(login, password).process(
                {

                },
                {

                }
            )
        }
    }

}