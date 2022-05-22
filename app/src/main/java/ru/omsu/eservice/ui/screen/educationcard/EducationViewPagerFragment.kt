package ru.omsu.eservice.ui.screen.educationcard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentEducationViewPagerBinding
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.ui.screen.educationcard.adapter.EducationCardBaseInfoAdapter
import ru.omsu.eservice.ui.utils.launchWhenStart

@AndroidEntryPoint
class EducationViewPagerFragment : Fragment(R.layout.fragment_education_view_pager) {

    companion object {

        private const val EDUCATION_GROUP_UI = "educationGroupUi"

        fun create(educationGroupUi: EducationGroupUi): EducationViewPagerFragment {
            val fragment = EducationViewPagerFragment()
            val bundle = Bundle()
            bundle.putParcelable(EDUCATION_GROUP_UI, educationGroupUi)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: EducationViewPagerViewModel by viewModels()
    private val binding: FragmentEducationViewPagerBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.baseInfoState.onEach {
            (binding.baseInfoRecycler.adapter as? EducationCardBaseInfoAdapter)?.submitList(it)
        }.launchWhenStart(lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            baseInfoRecycler.adapter = EducationCardBaseInfoAdapter()
        }
    }

}