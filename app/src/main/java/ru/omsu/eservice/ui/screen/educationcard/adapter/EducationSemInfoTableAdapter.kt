package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.databinding.ItemEducationSemInfoRowBinding
import ru.omsu.eservice.domain.model.EntriesSeminar


class EducationSemInfoRowAdapter : ListAdapter<EntriesSeminar,
        EducationSemInfoRowAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)


    class ViewHolder private constructor(
        private val binding: ItemEducationSemInfoRowBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EntriesSeminar) {
            with(binding) {
                title.text = item.discipline
                contentt.text = item.controlForm
                content.text = item.length.toString()
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemEducationSemInfoRowBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<EntriesSeminar>() {
        override fun areItemsTheSame(
            oldItem: EntriesSeminar,
            newItem: EntriesSeminar
        ): Boolean {
            return oldItem.planId == newItem.planId
        }

        override fun areContentsTheSame(
            oldItem: EntriesSeminar,
            newItem: EntriesSeminar
        ): Boolean {
            return oldItem == newItem
        }
    }
}