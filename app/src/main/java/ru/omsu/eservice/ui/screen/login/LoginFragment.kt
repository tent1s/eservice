package ru.omsu.eservice.ui.screen.login

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentLoginBinding
import ru.omsu.eservice.ui.utils.*


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        fun getNewInstance() = LoginFragment()
    }

    private val binding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadingState.onEach {
            if (it) {
                binding.loginButton.hide()
                binding.progressBar.show()
            } else {
                binding.loginButton.show()
                binding.progressBar.hide()
            }
        }.launchWhenStart(lifecycle)

        viewModel.errorState.map {
            when (it) {
                is LoginViewModel.ErrorState.EmailError -> binding.emailTextInputLayout.error =
                    getString(it.textResource)
                is LoginViewModel.ErrorState.PasswordError -> binding.passwordTextInputLayout.error =
                    getString(it.textResource)
            }
        }.launchWhenStart(lifecycle)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window?.setBackgroundDrawable(requireContext().getDrawable(R.drawable.ll_background_top))
        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.emailInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
        binding.emailInput.afterTextChangedInFocus { binding.emailTextInputLayout.error = null }
        binding.passwordInput.afterTextChangedInFocus { binding.passwordTextInputLayout.error = null }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.setBackgroundDrawable(ColorDrawable(requireContext().color(R.color.white)))
    }


}