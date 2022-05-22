package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.databinding.ItemBaseInfoEducationCardBinding

class EducationCardBaseInfoAdapter : ListAdapter<Pair<Int, String>,
        EducationCardBaseInfoAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)


    class ViewHolder private constructor(
        private val binding: ItemBaseInfoEducationCardBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<Int, String>) {
            with(binding) {
                title.setText(item.first)
                content.text = item.second
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemBaseInfoEducationCardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<Pair<Int, String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ): Boolean {
            return oldItem == newItem
        }
    }
}