package ru.omsu.eservice.ui.screen.educationcard

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentEducationCardBinding
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.ui.utils.hide
import ru.omsu.eservice.ui.utils.launchWhenStart
import ru.omsu.eservice.ui.utils.show

@AndroidEntryPoint
class EducationCardFragment : Fragment(R.layout.fragment_education_card) {

    private val viewModel: EducationCardViewModel by viewModels()

    private val binding: FragmentEducationCardBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.educationCardState.onEach {
            when (it) {
                is EducationCardViewModel.EducationState.Data -> initViews(it.educationGroupUi)
                is EducationCardViewModel.EducationState.Error -> {
                    Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                    binding.progressBar.hide()
                }
                EducationCardViewModel.EducationState.Loading -> binding.progressBar.show()
            }
        }.launchWhenStart(lifecycle)
    }

    private fun initViews(list: List<EducationGroupUi>) {
        binding.progressBar.hide()
        binding.materialToolbar.setNavigationOnClickListener {
            viewModel.onBack()
        }
        binding.pager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return EducationViewPagerFragment.create(list[position])
            }

            override fun getItemCount(): Int {
                return list.size
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = list[position].summary?.group
        }.attach()
    }
}