package ru.omsu.eservice.ui.screen.educationcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.omsu.eservice.R

@AndroidEntryPoint
class EducationCardFragment : Fragment(R.layout.fragment_education_card) {

    private val viewModel : EducationCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.vv()
    }
}