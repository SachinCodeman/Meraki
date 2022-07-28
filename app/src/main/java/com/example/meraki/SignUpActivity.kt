package com.example.meraki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.meraki.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        // Check validations

        userFocusListener()
        emailFocusListener()
        passwordFocusListener()


        binding.loginReference.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.registerBtn.setOnClickListener {

            val username = binding.signupUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            val email = binding.signupEmail.text.toString()


            when {
                TextUtils.isEmpty(username) -> {
                    binding.signupTlUsername.error = "Please enter your Username"
                    binding.signupTlUsername.requestFocus()

                }
                TextUtils.isEmpty(email) -> {
                    binding.signupTlEmail.error = "Please enter your email"
                    binding.signupTlEmail.requestFocus()

                }
                TextUtils.isEmpty(password) -> {
                    binding.signupTlPassword.error = "Please enter your password"
                    binding.signupTlPassword.requestFocus()
                }
                password.length != 8-> {
                    binding.signupTlPassword.error = "Password must be of 8  characters"
                    binding.signupTlPassword.requestFocus()
                }
                else -> {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                Toast.makeText(this,"User Created Successfully", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                }

            }





        }

    }

    // Checks the user input & set a error text if something is incorrect
    private fun userFocusListener() {

        binding.signupUsername.setOnFocusChangeListener { _, focused ->

            val username = binding.signupUsername.text.toString()

            if (!focused) {
                if (username.length > 15) {
                    binding.signupTlUsername.error = "Username must be of 15 or less characters."
                } else {
                    binding.signupTlUsername.error = null
                }

            }

        }
    }


    // Checks the user input  using validPassword() & set a helper text if something is incorrect
    private fun passwordFocusListener() {

        binding.signupPassword.setOnFocusChangeListener { _, focused ->

            if (focused) {
                binding.signupTlPassword.helperText = validPassword()
            }

        }
    }

    // Checks if the input of the users are valid . If not it returns  a   text to passwordFocusListener
    private fun validPassword(): CharSequence? {

        val password = binding.signupPassword.text.toString()
        if (password.length != 8) {
            return "Password must be of 8  characters"
        }
        else {
            binding.signupTlPassword.error = null

            return null
        }
    }


    // Checks the user input  using validEmail() & set a helper text if something is incorrect
    private fun emailFocusListener() {

        binding.signupEmail.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.signupTlEmail.error = validEmail()
            }

        }
    }

    // Checks if the input of the users are valid . If not it returns  a  text to emailFocusListener
    private fun validEmail(): CharSequence? {

        val email = binding.signupEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid Email.  Correct eg:-(a-z.0-9@gmail.com)"
        } else {
            binding.signupTlEmail.error = null
            return null
        }
    }


}