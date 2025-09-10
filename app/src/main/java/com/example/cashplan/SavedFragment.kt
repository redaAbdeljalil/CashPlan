package com.example.cashplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.example.cashplan.R
import com.example.cashplan.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedPagerAdapter: SavedPagerAdapter
    private val tabTitles = listOf("All", "Trips", "Hotels", "Restaurants", "Activities")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupClickListeners()
        checkSavedItems()
    }

    private fun setupViewPager() {
        savedPagerAdapter = SavedPagerAdapter(this)
        binding.savedViewPager.adapter = savedPagerAdapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(binding.savedTabs, binding.savedViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupClickListeners() {
        binding.exploreButton.setOnClickListener {
            // Navigate to explore fragment
            navigateToExplore()
        }
    }

    private fun checkSavedItems() {
        // Check if user has saved items
        val hasSavedItems = getSavedItemsCount() > 0

        if (hasSavedItems) {
            binding.savedViewPager.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        } else {
            binding.savedViewPager.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        }
    }

    private fun getSavedItemsCount(): Int {
        // Get saved items count from SharedPreferences or database
        val sharedPref = requireActivity().getSharedPreferences("SavedItems", 0)
        return sharedPref.getInt("saved_count", 0)
    }

    private fun navigateToExplore() {
        // Get the HomeActivity and switch to explore tab
        (requireActivity() as? com.example.cashplan.HomeActivity)?.let { homeActivity ->
            homeActivity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId = R.id.nav_explore
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ViewPager2 Adapter for different saved categories
    private inner class SavedPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabTitles.size

        override fun createFragment(position: Int): Fragment {
            return SavedCategoryFragment.newInstance(position)
        }
    }
}

// Fragment for each saved category
class SavedCategoryFragment : Fragment() {

    private var categoryPosition: Int = 0

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): SavedCategoryFragment {
            val fragment = SavedCategoryFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryPosition = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create a simple view for each category
        val view = View(requireContext())
        view.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load saved items for this category
        loadSavedItemsForCategory(categoryPosition)
    }

    private fun loadSavedItemsForCategory(category: Int) {
        // Implement loading saved items based on category
        val categoryName = when (category) {
            0 -> "All"
            1 -> "Trips"
            2 -> "Hotels"
            3 -> "Restaurants"
            4 -> "Activities"
            else -> "All"
        }

        // For now, just show a toast
        // You can implement RecyclerView with saved items here
        Toast.makeText(requireContext(), "Loading $categoryName saved items", Toast.LENGTH_SHORT).show()
    }
}