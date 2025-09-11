package com.example.cashplan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    // Define the data for the onboarding screens.
    // This makes it easy to add or remove screens in the future.
    private val onboardingData = listOf(
        OnboardingContent(
            "Take Control of Your Finances",
            "Start your journey to financial freedom with our easy-to-use budget planning tools and smart insights.",
            R.drawable.img
        ),
        OnboardingContent(
            "Track Your Spending",
            "Monitor your expenses and see where your money goes with detailed analytics and reports.",
            R.drawable.img
        ),
        OnboardingContent(
            "Achieve Your Goals",
            "Set savings goals and get personalized tips to reach your financial milestones faster.",
            R.drawable.img
        )
    )

    override fun createFragment(position: Int): Fragment {
        val data = onboardingData[position]
        // Use the newInstance factory to pass data to the fragment
        return OnboardingFragment.newInstance(
            data.title,
            data.description,
            data.imageRes
        )
    }

    override fun getItemCount(): Int {
        return onboardingData.size
    }
}

// A simple data class to hold content for an onboarding page
data class OnboardingContent(
    val title: String,
    val description: String,
    val imageRes: Int
)
