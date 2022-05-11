package ru.omsu.eservice.ui.screen

import android.window.SplashScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.omsu.eservice.ui.screen.educationcard.EducationCardFragment
import ru.omsu.eservice.ui.screen.login.LoginFragment
import ru.omsu.eservice.ui.screen.services.ServicesFragment
import ru.omsu.eservice.ui.screen.splash.SplashFragment


object Screens {

    fun loginScreen() = FragmentScreen {
        LoginFragment.getNewInstance()
    }

    fun splashScreen() = FragmentScreen {
        SplashFragment()
    }

    fun servicesScreen() = FragmentScreen {
        ServicesFragment()
    }

    fun educationCardScreen() = FragmentScreen {
        EducationCardFragment()
    }

}