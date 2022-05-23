package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.databinding.ItemEducationCardEntriesBinding
import ru.omsu.eservice.domain.model.Entries


class EducationEntriesAdapter : ListAdapter<Entries,
        EducationEntriesAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)


    class ViewHolder private constructor(
        private val binding: ItemEducationCardEntriesBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Entries) {
            with(binding) {
                title.text = item.subject
                entries.text = item.result
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemEducationCardEntriesBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<Entries>() {
        override fun areItemsTheSame(
            oldItem: Entries,
            newItem: Entries
        ): Boolean {
            return oldItem.subject == newItem.subject
        }

        override fun areContentsTheSame(
            oldItem: Entries,
            newItem: Entries
        ): Boolean {
            return oldItem == newItem
        }
    }
}