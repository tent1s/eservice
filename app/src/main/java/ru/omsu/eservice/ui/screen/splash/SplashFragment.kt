package ru.omsu.eservice.ui.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentSplashBinding
import ru.omsu.eservice.ui.utils.getAnimation
import ru.omsu.eservice.ui.utils.show

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding: FragmentSplashBinding by viewBinding()

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animation = requireActivity().getAnimation(R.anim.anim_fadein)
        with(binding.loginButton) {
            setOnClickListener {
                viewModel.nextScreenClick()
            }
            startAnimation(animation)
            show()
        }
        viewModel.onViewCreated()
    }

}