package com.example.cashplan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cashplan.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to SignUpActivity
        binding.signUpLink.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Navigate back (assuming you want to go to MainActivity)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Start the MainActivity
        }

        // Navigate to HomeActivity (assuming HomeFragment is hosted by MainActivity or another target Activity)
        binding.loginButton.setOnClickListener {
            // If HomeFragment is hosted within MainActivity, you'd navigate to MainActivity
            // and then ensure HomeFragment is displayed.
            // If HomeFragment is a standalone Activity (which is less common for Fragments):
            // val intent = Intent(this, HomeFragment::class.java) // Only if HomeFragment is an Activity

            // Assuming you want to go to MainActivity, which might then display HomeFragment:
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent) // Start the target Activity

            // Optional: Finish the current activity so the user can't go back to Login
            finish()
        }
    }
}