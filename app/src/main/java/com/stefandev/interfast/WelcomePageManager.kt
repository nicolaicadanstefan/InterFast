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
import android.widget.Button
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
        val startButton = activity.findViewById<Button>(R.id.start_button)

        val lettersDelay = 250L
        var currentDelay = 1000L
        val imageViewIds = listOf(
            R.id.i, R.id.n, R.id.t, R.id.e,
            R.id.r
        )

        imageViewIds.forEach { id ->
            val imageView = activity.findViewById<ImageView>(id)
            animateLetter(imageView, currentDelay) { }
            currentDelay += lettersDelay
        }

        // Fade for Quote
        Handler(Looper.getMainLooper()).postDelayed({
            val quoteImageView = activity.findViewById<ImageView>(R.id.quoteImageView)
            quoteImageView.visibility = View.VISIBLE
            quoteImageView.startAnimation(fadeInAnimation)
        }, 3800)

        // Fade for button
        Handler(Looper.getMainLooper()).postDelayed({
            startButton.visibility = View.VISIBLE
            startButton.startAnimation(fadeInAnimation)
        }, 3800)

        startButton.setOnClickListener {
            val introductionPage = IntroductionPage(activity)
            introductionPage.showIntroductionPage()
        }

        // Declare ImageView objects for the "f", "a", "s" and "t" letters
        val imageViewLetterF = activity.findViewById<ImageView>(R.id.f)
        val imageViewLetterA = activity.findViewById<ImageView>(R.id.a)
        val imageViewLetterS = activity.findViewById<ImageView>(R.id.s)
        val imageViewLetterTfade = activity.findViewById<ImageView>(R.id.t_fade)

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
        animateLetter(imageViewLetterF, 3000, true) {
            applyBlurEffect(imageViewLetterF)
        }

        // Animate the "a" letter and apply blur after animation
        animateLetter(imageViewLetterA, 3000, true) {
            applyBlurEffect(imageViewLetterA)
        }

        // Animate the "s" letter and apply blur after animation
        animateLetter(imageViewLetterS, 3000, true) {
            applyBlurEffect(imageViewLetterS)
        }

        // Animate the "t" letter and apply blur after animation
        animateLetter(imageViewLetterTfade, 3000, true) {
            applyBlurEffect(imageViewLetterTfade)
        }
    }

    // Function that animates the letters and make that bouncy effect
    private fun animateLetter(
        letter: ImageView,
        delay: Long,
        fromRight: Boolean = false,
        onAnimationEnd: () -> Unit
    ) {
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
                    onAnimationEnd()
                }
                .start()
        }, delay)
    }

    // Blur effect after the animation
    private fun applyBlurEffect(imageView: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imageView.setRenderEffect(RenderEffect.createBlurEffect(4f, 4f, Shader.TileMode.MIRROR))
        }
    }

    // Function to fade the elements from welcome page to the introduction page
    private fun navigateToIntroductionPage() {
        val introductionPageView = activity.layoutInflater.inflate(
            R.layout.introduction_page,
            activity.findViewById(android.R.id.content),
            false
        )

        val viewsToFadeOut = listOf<View>(
            activity.findViewById(R.id.LightingBolt),
            activity.findViewById(R.id.start_button),
            activity.findViewById(R.id.quoteImageView),
            activity.findViewById(R.id.i),
            activity.findViewById(R.id.n),
            activity.findViewById(R.id.t),
            activity.findViewById(R.id.e),
            activity.findViewById(R.id.r),
            activity.findViewById(R.id.f),
            activity.findViewById(R.id.a),
            activity.findViewById(R.id.s),
            activity.findViewById(R.id.t_fade)
        )

        viewsToFadeOut.forEach { view ->
            view.animate().apply {
                alpha(0f)
                duration = 800
                withEndAction {
                    activity.setContentView(introductionPageView)
                }
                start()
            }
        }
    }
}
