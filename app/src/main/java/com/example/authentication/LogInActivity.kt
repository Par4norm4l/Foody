package com.example.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.logInButton
import kotlinx.android.synthetic.main.activity_log_in.signUpButton

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()
        read()

        supportActionBar?.hide()
    }

    private fun init() {
        auth = Firebase.auth
        logInButton.setOnClickListener() {
            signIn()
        }
        signUpButton.setOnClickListener() {
            openSignUp()
        }

        sharedPreference = getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    private fun signIn() {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = "Please enter Email"
            emailEditText.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = "Please enter valid Email"
            emailEditText.requestFocus()
        }
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Please enter Password"
            passwordEditText.requestFocus()
        }


        if (emailEditText.text.toString().isNotEmpty() && passwordEditText.text.toString()
                        .isNotEmpty()
        ) {
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            d("logIN", "signInWithEmail:success")
                            val user = auth.currentUser
                            openCharacterCreation()
                        } else {
                            d("logIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
        }
    }

    private fun openCharacterCreation() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun openSignUp() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun checkbox(view: View) {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

     if(email.isNotEmpty() && password.isNotEmpty()){val editor = sharedPreference.edit()
         editor.putString("email", email)
         editor.putString("password", password)

         editor.apply()
         Toast.makeText(this, "Information saved", Toast.LENGTH_SHORT).show()
     }else
         Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

    }

    private fun read() {

        val email = sharedPreference.getString("email", "")
        val password = sharedPreference.getString("password", "")

        emailEditText.setText(email)
        passwordEditText.setText(password)


    }


}




