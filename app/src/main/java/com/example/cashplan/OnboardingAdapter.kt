package com.example.cashplan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment.newInstance(
                0,
                "Take Control of Your Finances", // Replace with string resource
                "Start your journey to financial freedom with our easy-to-use budget planning tools and smart insights.",
                R.drawable.img // Replace with your image resource
            )
            1 -> OnboardingFragment.newInstance(
                1,
                "Track Your Spending", // Replace with string resource
                "Monitor your expenses and see where your money goes with detailed analytics and reports.",
                R.drawable.img // Replace with your image resource
            )
            2 -> OnboardingFragment.newInstance(
                2,
                "Achieve Your Goals", // Replace with string resource
                "Set savings goals and get personalized tips to reach your financial milestones faster.",
                R.drawable.img // Replace with your image resource
            )
            else -> OnboardingFragment.newInstance(0, "", "", R.drawable.img)
        }
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }
}