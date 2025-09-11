package com.example.cashplan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.cashplan.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    // View Binding instance to access views without findViewById
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupClickListeners()
        updateUI(0)

        // Handle the back button press to navigate back through onboarding screens
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentItem = binding.viewPager.currentItem
                if (currentItem > 0) {
                    binding.viewPager.currentItem = currentItem - 1
                } else {
                    // Disable the callback and let the default back press behavior take over
                    // when on the first page
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(this)
        binding.viewPager.adapter = adapter

        // Register a callback to handle UI updates when the page changes
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUI(position)
            }
        })
    }

    private fun setupClickListeners() {
        // Navigate to the next onboarding page or to the LoginActivity
        binding.continueButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                // Navigate to the LoginActivity when on the last page
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Navigate to the previous onboarding page
        binding.backArrow.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem > 0) {
                binding.viewPager.currentItem = currentItem - 1
            }
        }
    }

    private fun updateUI(position: Int) {
        updateDots(position)
        // Only show the back arrow from the second page onwards
        binding.backArrow.isVisible = position > 0

        val buttonText = if (position == adapter.itemCount - 1) {
            getString(R.string.get_started)
        } else {
            getString(R.string.Continue)
        }
        binding.continueButton.text = buttonText
    }

    private fun updateDots(position: Int) {
        // Clear all previous dots to prevent duplicates
        binding.dotsContainer.removeAllViews()
        val dots = arrayOfNulls<ImageView>(adapter.itemCount)
        val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
        val activeDotSize = resources.getDimensionPixelSize(R.dimen.active_dot_size)

        // Dynamically create a dot for each onboarding page
        for (i in dots.indices) {
            dots[i] = ImageView(this).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@OnboardingActivity,
                        if (i == position) R.drawable.dot_active else R.drawable.dot_inactive
                    )
                )

                // Set different sizes for active and inactive dots
                val dotLayout = LinearLayout.LayoutParams(
                    if (i == position) activeDotSize else dotSize,
                    if (i == position) activeDotSize else dotSize
                ).apply {
                    setMargins(0, 0, 16, 0)
                }

                layoutParams = dotLayout
                binding.dotsContainer.addView(this)
            }
        }
    }
}
