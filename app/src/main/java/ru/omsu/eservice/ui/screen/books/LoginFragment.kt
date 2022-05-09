package ru.omsu.eservice.ui.screen.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentLoginBinding


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        fun getNewInstance() = LoginFragment()
    }

    private val binding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            viewModel.login("tent1s@yandex.ru", "Dh27fG99999999")
        }
    }


}