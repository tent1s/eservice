package ru.omsu.eservice.ui.screen.educationcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.omsu.eservice.data.device.database.EducationCardDao
import ru.omsu.eservice.data.device.database.EducationCardData
import ru.omsu.eservice.domain.interactor.EducationCardUseCase
import ru.omsu.eservice.domain.model.EducationGroupUi
import javax.inject.Inject

@HiltViewModel
class EducationCardViewModel @Inject constructor(
    private val router: Router,
    private val educationCardUseCase: EducationCardUseCase,
    private val educationCardDao: EducationCardDao
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            educationCardUseCase.educationCard().process(
                {
                    viewModelScope.launch(Dispatchers.IO) {
                        mutableEducationCardState.emit(EducationState.Error(it.message))

                        mutableEducationCardState.emit(EducationState.Data(educationCardDao.getAll().map {
                            it.educationGroupUi
                        }))
                    }
                },
                {
                    viewModelScope.launch(Dispatchers.IO) {
                        mutableEducationCardState.emit(EducationState.Data(it))
                        educationCardDao.insert(it.map {
                            EducationCardData(it.summary?.group.orEmpty(), it)
                        })
                    }
                }
            )
        }
    }

    private val mutableEducationCardState = MutableStateFlow<EducationState>(EducationState.Loading)
    val educationCardState: StateFlow<EducationState> =
        mutableEducationCardState.asStateFlow()


    sealed class EducationState {
        object Loading : EducationState()
        class Error(val errorMsg: String) : EducationState()
        class Data(val educationGroupUi: List<EducationGroupUi>) : EducationState()
    }

    fun onBack() {
        router.exit()
    }

}
