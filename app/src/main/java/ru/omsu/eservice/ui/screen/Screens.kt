package ru.omsu.eservice.ui.screen

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.omsu.eservice.ui.screen.books.LoginFragment


object Screens {

    fun searchBooksScreen() = FragmentScreen {
        LoginFragment.getNewInstance()
    }

}