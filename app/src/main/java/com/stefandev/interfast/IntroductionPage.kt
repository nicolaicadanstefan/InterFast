package com.stefandev.interfast

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class IntroductionPage(private val activity: AppCompatActivity) {
    private val fadeInAnimation: Animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in)

    @RequiresApi(Build.VERSION_CODES.S)
    fun showIntroductionPage() {
        activity.setContentView(R.layout.introduction_page)
        val thanksMessage: TextView = activity.findViewById(R.id.thanksMessage)
        val nextButton: Button = activity.findViewById(R.id.next_button)

        // Fade in the next button
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            nextButton.visibility = View.VISIBLE
            thanksMessage.visibility = View.VISIBLE
            thanksMessage.startAnimation(fadeInAnimation)
            nextButton.startAnimation(fadeInAnimation)
        }, 300)
        nextButton.setOnClickListener {
            navigateToMainPage()
        }
    }

    private fun navigateToMainPage() {
        try {
            activity.findNavController(R.id.nav_host_fragment).navigate(R.id.mainActivity)
        } catch (e: Exception) {
            e.printStackTrace()
            // Log or display any error messages
            Toast.makeText(activity, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
