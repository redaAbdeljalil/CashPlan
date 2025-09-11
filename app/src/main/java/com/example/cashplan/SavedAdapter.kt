package com.example.cashplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cashplan.databinding.ItemSavedBinding

class SavedAdapter(private var savedItems: List<SavedItem>) :
    RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    class SavedViewHolder(private val binding: ItemSavedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedItem) {
            binding.savedItemName.text = item.name
            binding.savedItemDescription.text = item.description
            binding.savedItemIcon.setImageResource(item.iconResId)
            binding.root.setOnClickListener {
                // Handle item click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding = ItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(savedItems[position])
    }

    override fun getItemCount(): Int = savedItems.size

    fun updateData(newItems: List<SavedItem>) {
        savedItems = newItems
        notifyDataSetChanged()
    }
}

data class SavedItem(
    val name: String,
    val description: String,
    val iconResId: Int,
    val category: String
)
