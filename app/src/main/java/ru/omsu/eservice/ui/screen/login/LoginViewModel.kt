package ru.omsu.eservice.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.omsu.eservice.R
import ru.omsu.eservice.domain.interactor.LoginUseCase
import ru.omsu.eservice.domain.repository.SessionRepository
import ru.omsu.eservice.ui.screen.Screens.servicesScreen
import ru.omsu.eservice.ui.utils.isEmail
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val router: Router,
    private val session: SessionRepository
) : ViewModel() {

    private val mutableLoadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = mutableLoadingState.asStateFlow()

    private val mutableErrorState = MutableSharedFlow<ErrorState>()
    val errorState: SharedFlow<ErrorState> = mutableErrorState.asSharedFlow()

    sealed class ErrorState {
        class EmailError(val textResource: Int) : ErrorState()
        class PasswordError(val textResource: Int) : ErrorState()
    }

    fun login(login: String, password: String) {
        if (!validate(login, password)) return
        mutableLoadingState.value = true
        viewModelScope.launch {
            loginUseCase.login(login, password).process(
                {
                    mutableLoadingState.value = false
                    viewModelScope.launch {
                        mutableErrorState.emit(ErrorState.PasswordError(R.string.auth_error))
                    }
                },
                {
                    mutableLoadingState.value = false
                    router.replaceScreen(servicesScreen())
                    session.newSessionBeCreated()
                }
            )
        }
    }

    private fun validate(login: String, password: String): Boolean {
        var isValid = true
        if (!login.isEmail() || login.isBlank()) {
            isValid = false
            viewModelScope.launch {
                mutableErrorState.emit(ErrorState.EmailError(R.string.invalid_email))
            }
        }
        if (password.isBlank()) {
            isValid = false
            viewModelScope.launch {
                mutableErrorState.emit(ErrorState.PasswordError(R.string.invalid_password))
            }
        }
        return isValid
    }

}