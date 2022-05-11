package ru.omsu.eservice.ui.screen.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.omsu.eservice.databinding.ItemServiceBinding
import ru.omsu.eservice.ui.screen.services.model.ServiceItem
import ru.omsu.eservice.ui.utils.setVisible


class ServiceAdapter(private val selectListener: OnClickListener) : ListAdapter<ServiceItem,
        ServiceAdapter.ViewHolder>(BooksInfoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent, selectListener)


    class ViewHolder private constructor(
        private val binding: ItemServiceBinding,
        private val selectListener: OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ServiceItem) {
            with(binding) {
                Glide.with(root.context).load(item.imageRes).into(icon)
                title.setText(item.title)
                description.setText(item.description)
                descriptionMore.setText(item.moreDescription)
                more.setOnClickListener {
                    selectListener.onShowMoreClicked(item)
                }
                binding.root.setOnClickListener {
                    selectListener.onCardClicked(item)
                }
                more.isSelected = item.showMore
                descriptionMore.setVisible(item.showMore)
            }

        }

        companion object {
            fun from(parent: ViewGroup, selectListener: OnClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemServiceBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, selectListener)
            }
        }
    }


    class BooksInfoDiffCallback : DiffUtil.ItemCallback<ServiceItem>() {
        override fun areItemsTheSame(oldItem: ServiceItem, newItem: ServiceItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ServiceItem, newItem: ServiceItem): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onShowMoreClicked(serviceItem: ServiceItem)
        fun onCardClicked(serviceItem: ServiceItem)
    }
}