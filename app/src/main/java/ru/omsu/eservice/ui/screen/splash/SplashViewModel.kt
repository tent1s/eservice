package ru.omsu.eservice.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.omsu.eservice.ui.screen.Screens.loginScreen
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {


    fun nextScreenClick() {
        router.replaceScreen(loginScreen())
    }
}