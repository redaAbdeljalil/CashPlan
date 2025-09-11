package com.example.cashplan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashplan.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val categories = listOf("Beaches", "Mountains", "Cities", "Adventure", "Relaxation", "Cultural")
    private val destinations = mutableListOf<Destination>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupClickListeners()
        loadSampleData()
    }

    private fun setupViews() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterDestinations(s.toString())
            }
        })

        setupCategoriesRecyclerView()
        setupDestinationsRecyclerView()
    }

    private fun setupClickListeners() {
        binding.filterButton.setOnClickListener {
            showToast("Filter clicked - implement filter dialog")
        }
    }

    private fun setupCategoriesRecyclerView() {
        showToast("Categories loaded: ${categories.size} items")
    }

    private fun setupDestinationsRecyclerView() {
        binding.destinationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showToast("Destinations setup complete")
    }

    private fun loadSampleData() {
        destinations.apply {
            clear()
            add(Destination("Paris, France", "From $800", "Romantic city with amazing architecture"))
            add(Destination("Tokyo, Japan", "From $1200", "Modern city with rich culture"))
            add(Destination("Bali, Indonesia", "From $600", "Tropical paradise with beautiful beaches"))
            add(Destination("New York, USA", "From $900", "The city that never sleeps"))
            add(Destination("Rome, Italy", "From $700", "Ancient history and amazing food"))
        }

        showToast("${destinations.size} destinations loaded")
    }

    private fun filterDestinations(query: String) {
        if (query.isEmpty()) {
            showToast("Showing all destinations")
        } else {
            val filtered = destinations.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            showToast("Found ${filtered.size} results for '$query'")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class Destination(
        val name: String,
        val price: String,
        val description: String
    )
}