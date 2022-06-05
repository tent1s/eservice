package ru.omsu.eservice.ui.screen.services

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.omsu.eservice.domain.repository.SessionRepository
import ru.omsu.eservice.ui.screen.Screens.educationCardScreen
import ru.omsu.eservice.ui.screen.Screens.splashScreen
import ru.omsu.eservice.ui.screen.services.model.ServiceItem
import ru.omsu.eservice.ui.screen.services.model.Services
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val router: Router,
    private val session: SessionRepository
) : ViewModel() {

    private val mutableServiceState = MutableStateFlow(
        listOf(
            Services.EDUCATION_CARD
        ).map()
    )
    val serviceState: StateFlow<List<ServiceItem>> = mutableServiceState.asStateFlow()

    private fun List<Services>.map() =
        map {
            ServiceItem(
                it,
                it.imageRes,
                it.title,
                it.description,
                it.moreDescription
            )

        }

    fun onShowMoreClicked(serviceItem: ServiceItem) {
        val tempList = mutableServiceState.value.toMutableList()
        val index = mutableServiceState.value.indexOf(serviceItem)
        tempList[index] = tempList[index].copy(showMore = !tempList[index].showMore)
        mutableServiceState.value = tempList
    }

    fun onCardClicked(serviceItem: ServiceItem) {
        when (serviceItem.type) {
            Services.EDUCATION_CARD -> router.navigateTo(educationCardScreen())
        }
    }

    fun logout() {
        session.clear()
        router.newRootScreen(splashScreen())
    }

}