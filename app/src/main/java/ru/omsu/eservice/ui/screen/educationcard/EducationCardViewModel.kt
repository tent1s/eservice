package ru.omsu.eservice.ui.screen.educationcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.omsu.eservice.domain.interactor.EducationCardUseCase
import javax.inject.Inject

@HiltViewModel
class EducationCardViewModel @Inject constructor(
    private val router: Router,
    private val educationCardUseCase: EducationCardUseCase
) : ViewModel() {

    fun vv() {
        viewModelScope.launch {
            educationCardUseCase.educationCard()
        }
    }

}