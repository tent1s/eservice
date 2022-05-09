package com.example.testapplt.ui.screen

import com.example.testapplt.ui.screen.books.LoginFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen


object Screens {

    fun searchBooksScreen() = FragmentScreen {
        LoginFragment.getNewInstance()
    }

}