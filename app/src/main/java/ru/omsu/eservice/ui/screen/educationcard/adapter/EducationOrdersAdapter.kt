package ru.omsu.eservice.ui.screen.educationcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.eservice.databinding.ItemEducationCardOrderBinding
import ru.omsu.eservice.ui.screen.educationcard.model.EducationOrderUi
import ru.omsu.eservice.ui.utils.hide
import ru.omsu.eservice.ui.utils.setVisible
import ru.omsu.eservice.ui.utils.show


class EducationOrdersAdapter(private val selectListener: OnClickListener) :
    ListAdapter<EducationOrderUi,
            EducationOrdersAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent, selectListener)


    class ViewHolder private constructor(
        private val binding: ItemEducationCardOrderBinding,
        private val selectListener: OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EducationOrderUi) {
            with(binding) {
                text.text = item.mainText
                text.isSelected = item.isShowingMore
                text.setOnClickListener {
                    selectListener.onShowMoreClicked(item)
                }
                if (item.moreInformation.isNotEmpty()) {
                    moreInformation.text = item.moreInformation
                    moreInformation.setVisible(item.isShowingMore)
                } else {
                    moreInformation.hide()
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup, selectListener: OnClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemEducationCardOrderBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, selectListener)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<EducationOrderUi>() {
        override fun areItemsTheSame(
            oldItem: EducationOrderUi,
            newItem: EducationOrderUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EducationOrderUi,
            newItem: EducationOrderUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnClickListener {
    fun onShowMoreClicked(item: EducationOrderUi)
}