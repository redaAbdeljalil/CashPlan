package com.example.cashplan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import android.view.ViewGroup
import android.content.res.Resources

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnboardingAdapter
    private lateinit var continueButton: AppCompatButton
    private lateinit var backArrow: ImageView
    private lateinit var dot1: View
    private lateinit var dot2: View
    private lateinit var dot3: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        initViews()
        setupViewPager()
        setupClickListeners()
        updateUI(0)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) {
                    viewPager.currentItem = currentItem - 1
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        continueButton = findViewById(R.id.continueButton)
        backArrow = findViewById(R.id.backArrow)
        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUI(position)
            }
        })
    }

    private fun setupClickListeners() {
        continueButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        backArrow.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }
    }

    private fun updateUI(position: Int) {
        updateDots(position)
        backArrow.isVisible = position > 0

        val buttonText = if (position == adapter.itemCount - 1) {
            getString(R.string.get_started)
        } else {
            getString(R.string.Continue)
        }
        continueButton.text = buttonText
    }

    private fun updateDots(position: Int) {
        dot1.setBackgroundResource(R.drawable.dot_inactive)
        dot2.setBackgroundResource(R.drawable.dot_inactive)
        dot3.setBackgroundResource(R.drawable.dot_inactive)

        setDotSize(dot1, 8)
        setDotSize(dot2, 8)
        setDotSize(dot3, 8)

        when (position) {
            0 -> {
                dot1.setBackgroundResource(R.drawable.dot_active)
                setDotSize(dot1, 12)
            }
            1 -> {
                dot2.setBackgroundResource(R.drawable.dot_active)
                setDotSize(dot2, 12)
            }
            2 -> {
                dot3.setBackgroundResource(R.drawable.dot_active)
                setDotSize(dot3, 12)
            }
        }
    }

    private fun setDotSize(dot: View, sizeDp: Int) {
        val density = Resources.getSystem().displayMetrics.density
        val sizePx = (sizeDp * density).toInt()

        val layoutParams = dot.layoutParams
        layoutParams.width = sizePx
        layoutParams.height = sizePx
        dot.layoutParams = layoutParams
    }


}