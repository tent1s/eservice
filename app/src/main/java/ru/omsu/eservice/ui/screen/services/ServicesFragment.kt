package ru.omsu.eservice.ui.screen.services

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.FragmentServicesBinding
import ru.omsu.eservice.ui.screen.services.model.ServiceItem
import ru.omsu.eservice.ui.utils.color
import ru.omsu.eservice.ui.utils.launchWhenStart


@AndroidEntryPoint
class ServicesFragment : Fragment(R.layout.fragment_services), ServiceAdapter.OnClickListener {

    private val viewModel: ServicesViewModel by viewModels()

    private val binding: FragmentServicesBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.serviceState.onEach {
            (binding.serviceList.adapter as? ServiceAdapter)?.submitList(it)
        }.launchWhenStart(lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window?.setBackgroundDrawable(ColorDrawable(requireContext().color(R.color.white)))
        binding.serviceList.adapter = ServiceAdapter(this)
        binding.serviceList.itemAnimator = null
        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    viewModel.logout()
                    true
                }
                else -> false
            }
        }
    }

    override fun onShowMoreClicked(serviceItem: ServiceItem) {
        viewModel.onShowMoreClicked(serviceItem)
    }

    override fun onCardClicked(serviceItem: ServiceItem) {
        viewModel.onCardClicked(serviceItem)
    }
}