package ru.omsu.eservice.ui.screen.educationcard

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentEducationViewPagerBinding
import ru.omsu.eservice.domain.model.Documents
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.domain.model.EntriesSeminar
import ru.omsu.eservice.ui.screen.educationcard.adapter.EducationCardBaseInfoAdapter
import ru.omsu.eservice.ui.screen.educationcard.adapter.EducationDocumentsAdapter
import ru.omsu.eservice.ui.screen.educationcard.adapter.EducationSemInfoRowAdapter
import ru.omsu.eservice.ui.utils.launchWhenStart
import ru.omsu.eservice.ui.utils.setVisible


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

        viewModel.baseSemInfoState.onEach {
            initSemInfoTable(it)
        }.launchWhenStart(lifecycle)

        viewModel.baseSemCountState.onEach {
            val adapter = ArrayAdapter(requireContext(), R.layout.item_list_menu, it)
            (binding.educationCardPlan.semsTextEdit as? AutoCompleteTextView)?.setAdapter(adapter)
            binding.educationCardPlan.semsTextEdit.setText(
                getString(
                    R.string.semest,
                    (viewModel.selectedSem + 1).toString()
                ), false
            )
            binding.educationCardPlan.semsTextEdit.setOnItemClickListener { _, _, i, _ ->
                viewModel.selectSem(i)
            }
        }.launchWhenStart(lifecycle)

        viewModel.baseSemInfoVisibleState.onEach {
            binding.educationCardPlan.educationPlanCardMore.isSelected = it
            binding.educationCardPlan.educationPlanGroup.setVisible(it)
        }.launchWhenStart(lifecycle)

        viewModel.baseDocumentsVisibleState.onEach {
            binding.educationCardDocuments.educationCardDocumentsMore.isSelected = it
            binding.educationCardDocuments.educationDocumentsTableRow.setVisible(it)
        }.launchWhenStart(lifecycle)

        viewModel.documentsState.onEach {
            initDocumentsList(it)
        }.launchWhenStart(lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            baseInfoRecycler.adapter = EducationCardBaseInfoAdapter()
            educationCardPlan.educationPlanTableRow.adapter = EducationSemInfoRowAdapter()
            educationCardPlan.educationPlanCardMore.setOnClickListener {
                viewModel.setEducationSemInfoVisibleState()
            }
            educationCardDocuments.educationCardDocumentsMore.setOnClickListener {
                viewModel.setEducationDocumentsVisibleState()
            }
            educationCardDocuments.educationDocumentsTableRow.adapter = EducationDocumentsAdapter()
        }
    }


    private fun initSemInfoTable(list: List<EntriesSeminar>?) {
        if (list.isNullOrEmpty()) return

        (binding.educationCardPlan.educationPlanTableRow.adapter as? EducationSemInfoRowAdapter)
            ?.submitList(list)
    }


    private fun initDocumentsList(list: List<Documents>?) {
        (binding.educationCardDocuments.educationDocumentsTableRow.adapter as? EducationDocumentsAdapter)
            ?.submitList(list)
    }

}