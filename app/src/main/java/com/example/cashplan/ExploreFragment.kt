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

    private val categories = listOf(
        Category("Beaches", R.drawable.ic_beach, R.color.primary_color),
        Category("Mountains", R.drawable.ic_mountain, R.color.secondary_color),
        Category("Cities", R.drawable.ic_city, R.color.accent_orange),
        Category("Adventure", R.drawable.ic_activities, R.color.accent_green),
        Category("Relaxation", R.drawable.ic_relax, R.color.accent_purple),
        Category("Cultural", R.drawable.ic_cultural, R.color.primary_color)
    )
    private val destinations = mutableListOf<Destination>()
    private lateinit var destinationsAdapter: DestinationAdapter

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

        loadSampleData()
        setupViews()
        setupClickListeners()
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
        binding.filterButtonCard.setOnClickListener {
            showToast("Filter clicked - implement filter dialog")
        }
    }

    private fun setupCategoriesRecyclerView() {
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val categoryAdapter = CategoryAdapter(categories)
        binding.categoriesRecyclerView.adapter = categoryAdapter
    }

    private fun setupDestinationsRecyclerView() {
        destinationsAdapter = DestinationAdapter(destinations)
        binding.destinationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.destinationsRecyclerView.adapter = destinationsAdapter
    }

    private fun loadSampleData() {
        destinations.apply {
            clear()
            add(Destination("Paris, France", "From $800", "Romantic city with amazing architecture"))
            add(Destination("Tokyo, Japan", "From $1200", "Modern city with rich culture"))
            add(Destination("Bali, Indonesia", "From $600", "Tropical paradise with beautiful beaches"))
            add(Destination("New York, USA", "From $900", "The city that never sleeps"))
            add(Destination("Rome, Italy", "From $700", "Ancient history and amazing food"))
            add(Destination("Kyoto, Japan", "From $1100", "Traditional temples and gardens"))
            add(Destination("Rio de Janeiro, Brazil", "From $750", "Vibrant carnival and stunning beaches"))
        }

        if (::destinationsAdapter.isInitialized) {
            destinationsAdapter.updateData(destinations)
        }
    }

    private fun filterDestinations(query: String) {
        val filtered = if (query.isEmpty()) {
            destinations
        } else {
            destinations.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
        destinationsAdapter.updateData(filtered)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
