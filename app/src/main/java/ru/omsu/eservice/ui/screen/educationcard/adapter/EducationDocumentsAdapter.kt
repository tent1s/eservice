package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.databinding.ItemEducationDocumentBinding
import ru.omsu.eservice.databinding.ItemEducationSemInfoRowBinding
import ru.omsu.eservice.domain.model.Documents
import ru.omsu.eservice.ui.utils.hide


class EducationDocumentsAdapter : ListAdapter<Documents,
        EducationDocumentsAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)


    class ViewHolder private constructor(
        private val binding: ItemEducationDocumentBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Documents) {
            with(binding) {
                val firstLine = buildString {
                    item.series?.let {
                        append("$it ")
                    }
                    item.number?.let {
                        append("$it ")
                    }
                    item.type?.let {
                        append("$it ")
                    }
                }
                fistLine.text = firstLine
                item.issuedBy?.let {
                    secondLine.text = it
                } ?: run {
                    secondLine.hide()
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemEducationDocumentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<Documents>() {
        override fun areItemsTheSame(
            oldItem: Documents,
            newItem: Documents
        ): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(
            oldItem: Documents,
            newItem: Documents
        ): Boolean {
            return oldItem == newItem
        }
    }
}