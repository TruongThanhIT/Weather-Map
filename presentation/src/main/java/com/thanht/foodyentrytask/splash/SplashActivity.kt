package com.thanht.foodyentrytask.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.thanht.foodyentrytask.home.HomeActivity
import com.thanht.foodyentrytask.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val DELAY_TIME = 2000L

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    private val splashViewModel: SplashViewModel by viewModels()

    private val runnable = Runnable {
        splashViewModel.userLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler().also {
            it.postDelayed(runnable,
                DELAY_TIME
            )
        }
        splashViewModel.splashState.observe(this, Observer {
            val splashState = it ?: return@Observer
            if (splashState is SplashState.HomeActivity) {
                navigateToHome()
            } else {
                navigateToLogin()
            }
            finish()
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}