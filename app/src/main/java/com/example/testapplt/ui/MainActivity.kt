package com.example.testapplt.ui

import android.os.Bundle
import com.example.testapplt.R
import com.example.testapplt.ui.common.BaseNavigationActivity
import com.example.testapplt.ui.screen.Screens.searchBooksScreen
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseNavigationActivity() {

    override val navigator: AppNavigator
        get() = AppNavigator(this, R.id.container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            navigator.applyCommands(arrayOf<Command>(Replace(searchBooksScreen())))
    }

}