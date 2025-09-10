package com.example.cashplan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cashplan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val loadingTime: Long = 3000
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, loadingTime)

        binding.loadingProgressBar.progress = 0
        binding.loadingProgressBar.max = 100

        val handler = Handler(Looper.getMainLooper())
        val progressRunnable = object : Runnable {
            override fun run() {
                if (binding.loadingProgressBar.progress < 100) {
                    binding.loadingProgressBar.progress += 2
                    handler.postDelayed(this, loadingTime / 50)
                }
            }
        }
        handler.post(progressRunnable)
    }
}