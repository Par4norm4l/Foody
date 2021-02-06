package com.example.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        init()
    }
    private fun init(){
        Handler().postDelayed({
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }, 3000)



    }
}