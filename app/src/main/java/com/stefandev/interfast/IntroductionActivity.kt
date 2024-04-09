package com.stefandev.interfast

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class IntroductionActivity : AppCompatActivity() {
    private lateinit var fadeInAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introduction_page)

        // Transparent Status Bar
        WindowCompat.setDecorFitsSystemWindows(window,false)

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val thanksMessage: TextView = findViewById(R.id.thanksMessage)
        val nextButton: Button = findViewById(R.id.next_button)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            nextButton.visibility = View.VISIBLE
            thanksMessage.visibility = View.VISIBLE
            thanksMessage.startAnimation(fadeInAnimation)
            nextButton.startAnimation(fadeInAnimation)
        }, 300)

        nextButton.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}