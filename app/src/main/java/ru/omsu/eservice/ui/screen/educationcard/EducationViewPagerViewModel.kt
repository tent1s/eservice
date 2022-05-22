package ru.omsu.eservice.ui.screen.educationcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.omsu.eservice.R
import ru.omsu.eservice.domain.model.EducationGroupUi

class EducationViewPagerViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val EDUCATION_GROUP_UI = "educationGroupUi"
    }

    private val cardInfoItem = savedStateHandle.get<EducationGroupUi>(EDUCATION_GROUP_UI)


    private val mutableBaseInfoState =
        MutableStateFlow<List<Pair<Int, String>>>(createBaseInfoList())
    val baseInfoState: StateFlow<List<Pair<Int, String>>> = mutableBaseInfoState.asStateFlow()


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

}