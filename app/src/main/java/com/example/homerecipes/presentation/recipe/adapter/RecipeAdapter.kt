package com.example.homerecipes.presentation.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homerecipes.databinding.ItemRecipeBinding
import com.example.homerecipes.domain.model.RecipeDomain

// This is a RecyclerView adapter implementation, overriding a pre-built ListAdapter format
class RecipeAdapter: ListAdapter<RecipeDomain, RecipeAdapter.MyViewHolder>(DiffCallback()) {
    var click: (RecipeDomain) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /* By using the ListAdapter format, it's possible to use methods associated with the
        model type (passed in the signature of this class) like getItem()
        */
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(
        private val binding: ItemRecipeBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: RecipeDomain) = with(binding) {
            tvTitle.text = item.name
            root.setOnClickListener {
                click(item)
            }
        }
    }
}

/* For the correct workflow of this custom adapter, it needs a callback function to deal with the
possible item duplicated events (eventually thrown by RecyclerViews)
*/
class DiffCallback: DiffUtil.ItemCallback<RecipeDomain>() {
    override fun areItemsTheSame(oldItem: RecipeDomain, newItem: RecipeDomain): Boolean  = oldItem == newItem

    override fun areContentsTheSame(oldItem: RecipeDomain, newItem: RecipeDomain): Boolean = oldItem.id == newItem.id
}