package ru.omsu.eservice.ui.screen.educationcard

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.omsu.eservice.R
import ru.omsu.eservice.domain.interactor.EducationCardUseCase
import ru.omsu.eservice.domain.model.Documents
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.domain.model.EntriesSeminar
import ru.omsu.eservice.domain.model.Sessions
import ru.omsu.eservice.ui.screen.educationcard.model.EducationOrderUi
import javax.inject.Inject

@HiltViewModel
class EducationViewPagerViewModel @Inject constructor(
    bundle: SavedStateHandle,
    private val educationCardUseCase: EducationCardUseCase
) : ViewModel() {

    companion object {
        private const val EDUCATION_GROUP_UI = "educationGroupUi"
    }

    private val cardInfoItem = bundle.get<EducationGroupUi>(EDUCATION_GROUP_UI)


    private val mutableBaseInfoState =
        MutableStateFlow<List<Pair<Int, String>>>(createBaseInfoList())
    val baseInfoState: StateFlow<List<Pair<Int, String>>> = mutableBaseInfoState.asStateFlow()


    private val mutableSemInfoState =
        MutableStateFlow(cardInfoItem?.semInfo?.get(selectedSem)?.entries)
    val baseSemInfoState: StateFlow<List<EntriesSeminar>?> = mutableSemInfoState.asStateFlow()

    private val mutableSemCountState =
        MutableStateFlow(getSemCount())
    val baseSemCountState: StateFlow<List<String>> = mutableSemCountState.asStateFlow()

    private val mutableSemInfoVisibleState =
        MutableStateFlow(false)
    val baseSemInfoVisibleState: StateFlow<Boolean> = mutableSemInfoVisibleState.asStateFlow()

    private val mutableBaseDocumentsVisibleState =
        MutableStateFlow(false)
    val baseDocumentsVisibleState: StateFlow<Boolean> = mutableBaseDocumentsVisibleState.asStateFlow()

    private val mutableSessionsVisibleState =
        MutableStateFlow(false)
    val sessionsVisibleState: StateFlow<Boolean> = mutableSessionsVisibleState.asStateFlow()

    private val mutableOrdersVisibleState =
        MutableStateFlow(false)
    val ordersVisibleState: StateFlow<Boolean> = mutableOrdersVisibleState.asStateFlow()

    private var mutableSelectedSem = 0
    val selectedSem: Int get() = mutableSelectedSem

    private val mutableDocumentsState =
        MutableStateFlow(cardInfoItem?.documents)
    val documentsState: StateFlow<List<Documents>?> = mutableDocumentsState.asStateFlow()

    private val mutableSessionState =
        MutableStateFlow(cardInfoItem?.sessions)
    val sessionState: StateFlow<List<Sessions>?> = mutableSessionState.asStateFlow()

    private val mutableError = MutableSharedFlow<String>()
    val error: SharedFlow<String> = mutableError.asSharedFlow()

    private val mutableOpenPdf = MutableSharedFlow<Uri>()
    val openPdf: SharedFlow<Uri> = mutableOpenPdf.asSharedFlow()

    private val mutableOrdersState =
        MutableStateFlow(cardInfoItem?.decrees?.map {
            EducationOrderUi(
                it.id,
                "${it.number} ${it.about} \n${it.date}",
            )
        })
    val ordersState: StateFlow<List<EducationOrderUi>?> = mutableOrdersState.asStateFlow()


    private var getMoreInfoJob : Job? = null


    private fun getSemCount(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        cardInfoItem?.semInfo?.let { sems ->
            sems.forEach {
                list.add(it.number.toString() + " семестр")
            }
        }
        return list
    }


    private fun createBaseInfoList(): MutableList<Pair<Int, String>> {
        val list: MutableList<Pair<Int, String>> = mutableListOf()
        cardInfoItem?.summary?.let { sumInfo ->
            with(sumInfo) {
                fullName?.let { list.add(Pair(R.string.base_info_card_fio, it)) }
                birthDay?.let { list.add(Pair(R.string.base_info_card_birthday, it)) }
                if (cardInfoItem.addresses.isNotEmpty())
                    list.add(Pair(R.string.base_info_card_address, listOfAddressToString()))
                faculty?.let { list.add(Pair(R.string.base_info_card_faculty, it)) }
                specification?.let { list.add(Pair(R.string.base_info_card_education_program, it)) }
                studyForm?.let { list.add(Pair(R.string.base_info_card_form_education, it)) }
                course?.let { list.add(Pair(R.string.base_info_card_course_education, it)) }
                financing?.let { list.add(Pair(R.string.base_info_card_financy, it)) }
                studyLength?.let { list.add(Pair(R.string.base_info_card_education_time, it)) }
            }
        }

        return list
    }


    private fun listOfAddressToString(): String {
        var addresses = ""
        cardInfoItem?.addresses?.forEachIndexed { index, address ->
            addresses = if (index == 0)
                address.fullPath.toString()
            else
                addresses + "\n" + address.fullPath
        }
        return addresses
    }


    fun selectSem(number: Int) {
        mutableSelectedSem = number
        mutableSemInfoState.value = cardInfoItem?.semInfo?.get(selectedSem)?.entries
    }

    fun setEducationSemInfoVisibleState() {
        mutableSemInfoVisibleState.value = !baseSemInfoVisibleState.value
    }

    fun setEducationDocumentsVisibleState() {
        mutableBaseDocumentsVisibleState.value = !baseDocumentsVisibleState.value
    }

    fun setSessionVisibleState() {
        mutableSessionsVisibleState.value = !sessionsVisibleState.value
    }

    fun setOrdersVisibleState() {
        mutableOrdersVisibleState.value = !mutableOrdersVisibleState.value
    }

    fun scheduleClicked() {
        viewModelScope.launch {
            cardInfoItem?.scheduleUrl?.let { mutableOpenPdf.emit(Uri.parse(it)) }
        }
    }

    fun openObjectPdf(id: String?) {
        viewModelScope.launch {
            id?.let { mutableOpenPdf.emit(Uri.parse("https://eservice.omsu.ru/publish/rp/$id".trim())) }
        }
    }

    fun onShowMoreClicked(item: EducationOrderUi) {
        if (item.moreInformation.isNotEmpty()) {
            mutableOrdersState.value?.let {
                val tempList = it.toMutableList()
                val index = it.indexOf(item)
                if (tempList.getOrNull(index) == null) return
                tempList[index] =
                    tempList[index].copy(isShowingMore = !tempList[index].isShowingMore)
                mutableOrdersState.value = tempList
            }
        } else {
            getMoreInfoJob?.cancel()
            getMoreInfoJob = viewModelScope.launch {
                educationCardUseCase.moreInformationOrder(item.id!!).process(
                    {
                        viewModelScope.launch { mutableError.emit(it.message) }
                    },
                    { moreInfo ->
                        mutableOrdersState.value?.let {
                            val tempList = it.toMutableList()
                            val index = it.indexOf(item)
                            if (tempList.getOrNull(index) == null) return@let
                            tempList[index] =
                                tempList[index].copy(isShowingMore = !tempList[index].isShowingMore, moreInformation = moreInfo)
                            mutableOrdersState.value = tempList
                        }
                        Unit
                    }
                )
            }
        }
    }

}