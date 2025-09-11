package com.example.cashplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cashplan.databinding.ItemDestinationBinding

class DestinationAdapter(private var destinations: List<Destination>) :
    RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    class DestinationViewHolder(private val binding: ItemDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(destination: Destination) {
            binding.destinationName.text = destination.name
            binding.destinationDescription.text = destination.description
            binding.destinationImage.setImageResource(R.drawable.ic_explore)
            binding.root.setOnClickListener {
                // Handle item click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(destinations[position])
    }

    override fun getItemCount(): Int = destinations.size

    fun updateData(newDestinations: List<Destination>) {
        destinations = newDestinations
        notifyDataSetChanged()
    }
}

data class Destination(
    val name: String,
    val price: String,
    val description: String
)
