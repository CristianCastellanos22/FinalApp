package com.cristian.finalapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cristian.finalapp.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValidEmail(email) && isValidPassword(password)) {
                logInByEmail(email, password)
            } else {
                toast("Please fill all the data and confirm password is correct.")
            }
        }

        textViewForgotPassword.setOnClickListener {
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonCreateAccount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "Emial is not valid"
        }

        editTextPassword.validate {
            editTextPassword.error = if (isValidPassword(it)) null else "Password should " +
                    "contain 1 lowercase, 1 uppercase, 1 number, 1 special character and 4 characters length at least."
        }

    }

    private fun logInByEmail (email: String, password:String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                if (mAuth.currentUser!!.isEmailVerified) {
                    toast("User is now logged in.")
                } else {
                    toast("User must confirm email first")
                }
            } else {
                toast("An unexpected error occurred, please try again.")
            }
        }
    }

}
