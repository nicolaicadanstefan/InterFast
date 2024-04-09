package com.stefandev.interfast

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class WelcomeActivity : AppCompatActivity() {
    private lateinit var fadeInAnimation: Animation

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        // Transparent Status Bar
        WindowCompat.setDecorFitsSystemWindows(window,false)

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val lightingBoltImageView = findViewById<ImageView>(R.id.LightingBolt)
        lightingBoltImageView.startAnimation(fadeInAnimation)
        val startButton = findViewById<Button>(R.id.start_button)

        val lettersDelay = 250L
        var currentDelay = 1000L
        val imageViewIds = listOf(
            R.id.i, R.id.n, R.id.t, R.id.e,
            R.id.r
        )

        imageViewIds.forEach { id ->
            val imageView = findViewById<ImageView>(id)
            animateLetter(imageView, currentDelay)
            currentDelay += lettersDelay
        }

        // Fade for Quote
        Handler(Looper.getMainLooper()).postDelayed({
            val quoteImageView = findViewById<ImageView>(R.id.quoteImageView)
            quoteImageView.visibility = View.VISIBLE
            quoteImageView.startAnimation(fadeInAnimation)
        }, 3800)

        // Fade for button
        Handler(Looper.getMainLooper()).postDelayed({
            startButton.visibility = View.VISIBLE
            startButton.startAnimation(fadeInAnimation)
        }, 3800)

        startButton.setOnClickListener {
            navigateToIntroductionPage()
        }

        // Declare ImageView objects for the "f", "a", "s" and "t" letters
        val imageViewLetterF = findViewById<ImageView>(R.id.f)
        val imageViewLetterA = findViewById<ImageView>(R.id.a)
        val imageViewLetterS = findViewById<ImageView>(R.id.s)
        val imageViewLetterTfade = findViewById<ImageView>(R.id.t_fade)

        // Apply the blur for the letters "f", "a", "s" and "t" letters
        imageViewLetterF.setRenderEffect(
            RenderEffect.createBlurEffect(
                4f,
                4f,
                Shader.TileMode.MIRROR
            )
        )
        imageViewLetterA.setRenderEffect(
            RenderEffect.createBlurEffect(
                4f,
                4f,
                Shader.TileMode.MIRROR
            )
        )
        imageViewLetterS.setRenderEffect(
            RenderEffect.createBlurEffect(
                4f,
                4f,
                Shader.TileMode.MIRROR
            )
        )
        imageViewLetterTfade.setRenderEffect(
            RenderEffect.createBlurEffect(
                4f,
                4f,
                Shader.TileMode.MIRROR
            )
        )

        // Animate the "f" letter and apply blur after animation
        animateLetter(imageViewLetterF, 3000, true)

        // Animate the "a" letter and apply blur after animation
        animateLetter(imageViewLetterA, 3000, true)

        // Animate the "s" letter and apply blur after animation
        animateLetter(imageViewLetterS, 3000, true)

        // Animate the "t" letter and apply blur after animation
        animateLetter(imageViewLetterTfade, 3000, true)
    }

    // Function that animates the letters and make that bouncy effect
    private fun animateLetter(
        letter: ImageView,
        delay: Long,
        fromRight: Boolean = false
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            letter.alpha = 0f
            letter.visibility = View.VISIBLE
            if (fromRight) {
                letter.translationX = resources.displayMetrics.widthPixels.toFloat()
            } else {
                letter.translationY = -800f
            }
            letter.animate()
                .translationY(-200f)
                .translationX(0f)
                .alpha(1.0f)
                .setDuration(400)
                .withEndAction {
                    val bounceAnimator =
                        ObjectAnimator.ofFloat(letter, "translationY", -325f).apply {
                            repeatCount = 2
                            repeatMode = ObjectAnimator.REVERSE
                            duration = 200
                        }
                    bounceAnimator.start()
                }
                .start()
        }, delay)
    }

    // Function to navigate to IntroductionActivity
    private fun navigateToIntroductionPage() {
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
        finish()
    }
}