package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.R
import ru.omsu.eservice.databinding.ItemEducationCardSessionsBinding
import ru.omsu.eservice.domain.model.Sessions


class EducationSessionAdapter : ListAdapter<Sessions,
        EducationSessionAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)


    class ViewHolder private constructor(
        private val binding: ItemEducationCardSessionsBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = EducationEntriesAdapter()

        fun bind(item: Sessions) {
            with(binding) {
                title.text = root.context.getString(R.string.education_card_session, item.number)
                educationEntriesRecyclerView.adapter = adapter
                adapter.submitList(item.entries)
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemEducationCardSessionsBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<Sessions>() {
        override fun areItemsTheSame(
            oldItem: Sessions,
            newItem: Sessions
        ): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(
            oldItem: Sessions,
            newItem: Sessions
        ): Boolean {
            return oldItem == newItem
        }
    }
}