package com.example.homerecipes.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homerecipes.databinding.ItemFullRecipeBinding
import com.example.homerecipes.presentation.model.ItemListModel

class ItemListAdapter: ListAdapter<ItemListModel, ItemListAdapter.MyViewHolder>(DiffCallback()) {
    var click: (ItemListModel) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFullRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(
        private val binding: ItemFullRecipeBinding
    ): ViewHolder(binding.root) {
        fun bind(item: ItemListModel) = with(binding) {
            tvName.text = item.name
            root.setOnClickListener {
                click(item)
            }
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<ItemListModel>() {
    override fun areItemsTheSame(oldItem: ItemListModel, newItem: ItemListModel): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: ItemListModel, newItem: ItemListModel): Boolean  = oldItem.id == newItem.id
}