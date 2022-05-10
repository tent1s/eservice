package ru.omsu.eservice.ui

import android.os.Bundle
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.omsu.eservice.R
import dagger.hilt.android.AndroidEntryPoint
import ru.omsu.eservice.ui.common.BaseNavigationActivity
import ru.omsu.eservice.ui.screen.Screens.splashScreen


@AndroidEntryPoint
class MainActivity : BaseNavigationActivity() {

    override val navigator: AppNavigator
        get() = AppNavigator(this, R.id.container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            navigator.applyCommands(arrayOf<Command>(Replace(splashScreen())))
    }

}