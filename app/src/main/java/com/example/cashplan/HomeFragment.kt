package com.example.cashplan

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cashplan.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> getString(R.string.good_morning)
            in 12..17 -> getString(R.string.good_afternoon)
            else -> getString(R.string.good_evening)
        }
        binding.greetingText.text = greeting
        binding.userName.text = "John Doe" // To be replaced with dynamic data
        binding.budgetAmount.text = loadBudget()
    }

    private fun setupClickListeners() {
        binding.editBudget.setOnClickListener {
            showEditBudgetDialog()
        }

        binding.profileImage.setOnClickListener {
            navigateToProfile()
        }

        binding.planTripCard.setOnClickListener {
            showToast("Plan Trip clicked")
        }

        binding.findHotelsCard.setOnClickListener {
            showToast("Find Hotels clicked")
        }

        binding.restaurantsCard.setOnClickListener {
            showToast("Restaurants clicked")
        }

        binding.activitiesCard.setOnClickListener {
            showToast("Activities clicked")
        }
    }

    private fun showEditBudgetDialog() {
        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            hint = getString(R.string.enter_amount)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.edit_budget_title))
            .setView(editText)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val amount = editText.text.toString()
                if (amount.isNotEmpty()) {
                    updateBudget(amount)
                } else {
                    showToast(getString(R.string.invalid_budget))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun updateBudget(amount: String) {
        val formattedAmount = "$$amount"
        binding.budgetAmount.text = formattedAmount

        val sharedPref = requireActivity().getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("budget_amount", amount)
            apply()
        }

        showToast(getString(R.string.budget_updated))
    }

    private fun loadBudget(): String {
        val sharedPref = requireActivity().getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
        val amount = sharedPref.getString("budget_amount", "0.00")
        return "$$amount"
    }

    private fun navigateToProfile() {
        (requireActivity() as? HomeActivity)?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId = R.id.nav_profile
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
