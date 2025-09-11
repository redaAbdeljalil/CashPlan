package com.example.cashplan

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.cashplan.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupClickListeners()
        loadUserData()
    }

    private fun setupViews() {
        loadProfileData()
        loadStatistics()
    }

    private fun setupClickListeners() {
        setupMenuClickListeners()

        binding.logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setupMenuClickListeners() {
        val menuOptions = binding.menuOptions
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("UserProfile", 0)
        val userName = sharedPref.getString("user_name", getString(R.string.default_user_name))
        val userEmail = sharedPref.getString("user_email", getString(R.string.default_email))

        binding.profileName.text = userName
        binding.profileEmail.text = userEmail
    }

    private fun loadProfileData() {
        binding.profileName.text = getString(R.string.default_user_name)
        binding.profileEmail.text = getString(R.string.default_email)
        binding.memberSince.text = getString(R.string.member_since)
    }

    private fun loadStatistics() {
        val sharedPref = requireActivity().getSharedPreferences("UserStats", 0)

        val budgetSpent = sharedPref.getString("budget_spent", getString(R.string.budget_spent_default))
        val tripsCount = sharedPref.getString("trips_count", getString(R.string.trips_count_default))
        val savedCount = sharedPref.getString("saved_count", getString(R.string.saved_count_default))

        binding.budgetSpent.text = budgetSpent
        binding.tripsCount.text = tripsCount
        binding.savedCount.text = savedCount
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        val sharedPref = requireActivity().getSharedPreferences("UserSession", 0)
        with(sharedPref.edit()) {
            clear()
            apply()
        }

        val profilePref = requireActivity().getSharedPreferences("UserProfile", 0)
        with(profilePref.edit()) {
            clear()
            apply()
        }

        navigateToLogin()
    }

    private fun navigateToLogin() {
        try {
            val intent = Intent(requireContext(), Class.forName("com.yourpackage.cashplan.LoginActivity"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        } catch (e: ClassNotFoundException) {
            showToast("Login activity not found. Update the class name.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
        loadStatistics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}