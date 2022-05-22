package ru.omsu.eservice.ui.screen.educationcard

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentEducationViewPagerBinding
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.domain.model.SemInfo
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

        viewModel.baseSemInfoState.onEach {
            initTable(it, 1)
        }.launchWhenStart(lifecycle)

        viewModel.baseSemCountState.onEach {
            val adapter = ArrayAdapter(requireContext(), R.layout.item_list_menu, it)
            (binding.semsTextEdit as? AutoCompleteTextView)?.setAdapter(adapter)
        }.launchWhenStart(lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            baseInfoRecycler.adapter = EducationCardBaseInfoAdapter()
        }
    }


    private fun initTable(list: List<SemInfo>?, numberOfSem: Int) {
        if (list.isNullOrEmpty()) return

        val header = DataTableHeader.Builder()
            .item(getString(R.string.education_program_table_name), 20)
            .item(getString(R.string.education_program_table_control_form), 10)
            .item(getString(R.string.education_program_table_hour), 5)
            .build()

        val rows: ArrayList<DataTableRow> = ArrayList()

        list.find { it.number == numberOfSem }

        list.getOrNull(0)?.entries?.forEach {
            val row = DataTableRow.Builder()
                .value(it.discipline)
                .value(it.controlForm)
                .value(it.length.toString())
                .build()
            rows.add(row)
        }
        binding.educationPlanTable.cli
        with(binding.educationPlanTable) {
            setHeader(header)
            typeface = typeface
            setRows(rows)
            inflate(requireContext())
        }
    }

}