package com.cristian.finalapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cristian.finalapp.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_sing_up.*


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        buttonGoLogin.setOnClickListener {
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        buttonSingUp.setOnClickListener {
            val email = editTextEmailSign.text.toString()
            val password = editTextPasswordSign.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            if (isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password, confirmPassword)) {
                singUpByEmail(email, password)
            } else {
                toast("Please fill all the data and confirm password is correct.")
            }
        }

        editTextEmailSign.validate {
            editTextEmailSign.error = if (isValidEmail(it)) null else "Emial is not valid"
        }

        editTextPasswordSign.validate {
            editTextPasswordSign.error = if (isValidPassword(it)) null else "Password should " +
                    "contain 1 lowercase, 1 uppercase, 1 number, 1 special character and 4 characters length at least."
        }

        editTextConfirmPassword.validate {
            editTextConfirmPassword.error = if (isValidConfirmPassword(editTextPasswordSign.text.toString(), it)) null else "Confirm password does not match with"
        }
    }

    private fun singUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) {
                        toast("An email has been sent to you. Please, confirm before sign in")

                        goToActivity<LoginActivity> {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                } else {
                    toast("An unexpected error occurred, please try again")
                }
            }
    }

}
