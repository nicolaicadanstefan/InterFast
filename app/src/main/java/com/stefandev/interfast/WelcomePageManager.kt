package com.stefandev.interfast

import android.animation.ObjectAnimator
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class WelcomePageManager(private val activity: AppCompatActivity) {
    private val fadeInAnimation: Animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in)

    @RequiresApi(Build.VERSION_CODES.S)
    fun showWelcomePage() {
        activity.enableEdgeToEdge()
        activity.setContentView(R.layout.welcome_screen)

        val lightingBoltImageView = activity.findViewById<ImageView>(R.id.LightingBolt)
        lightingBoltImageView.startAnimation(fadeInAnimation)

        val lettersDelay = 250L
        var currentDelay = 1000L
        val imageViewIds = listOf(
            R.id.i, R.id.n, R.id.t, R.id.e,
            R.id.r
        )

        imageViewIds.forEach { id ->
            val imageView = activity.findViewById<ImageView>(id)
            animateLetter(imageView, currentDelay)
            currentDelay += lettersDelay
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val quoteImageView = activity.findViewById<ImageView>(R.id.quoteImageView)
            quoteImageView.visibility = View.VISIBLE
            quoteImageView.startAnimation(fadeInAnimation)
        }, 3800)

        // Declare ImageView objects for the "f", "a", "s" and "t" letters
        val imageViewLetterF = activity.findViewById<ImageView>(R.id.f)
        val imageViewLetterA = activity.findViewById<ImageView>(R.id.a)
        val imageViewLetterS = activity.findViewById<ImageView>(R.id.s)
        val imageViewLetterTfade = activity.findViewById<ImageView>(R.id.t_fade)

        // Apply the blur for the letters "f", "a", "s" and "t" letters
        imageViewLetterF.setRenderEffect(RenderEffect.createBlurEffect(7f,7f, Shader.TileMode.MIRROR))
        imageViewLetterA.setRenderEffect(RenderEffect.createBlurEffect(7f,7f, Shader.TileMode.MIRROR))
        imageViewLetterS.setRenderEffect(RenderEffect.createBlurEffect(7f,7f, Shader.TileMode.MIRROR))
        imageViewLetterTfade.setRenderEffect(RenderEffect.createBlurEffect(7f,7f, Shader.TileMode.MIRROR))

        // Animate the "f", "a", "s", and "t" letters
        animateLetter(imageViewLetterF, 3000, true)
        animateLetter(imageViewLetterA, 3000, true)
        animateLetter(imageViewLetterS, 3000, true)
        animateLetter(imageViewLetterTfade, 3000, true)
    }

    // Function that animates the letters and make that bouncy effect
    private fun animateLetter(letter: ImageView, delay: Long, fromRight: Boolean = false) {
        Handler(Looper.getMainLooper()).postDelayed({
            letter.alpha = 0f
            letter.visibility = View.VISIBLE
            if (fromRight) {
                letter.translationX = activity.resources.displayMetrics.widthPixels.toFloat()
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
}
