package com.example.meraki

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.meraki.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()



        emailFocusListener()
        passwordFocusListener()

        binding.signupReference.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgetPasswordBtn.setOnClickListener {
            val intent = Intent(this, ForgrtPassActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {


            val password = binding.password.text.toString()
            val email = binding.email.text.toString()


            when {
                TextUtils.isEmpty(email) -> {
                    binding.textInputEmail.error = "Please enter your email"
                    binding.textInputEmail.requestFocus()

                }
                TextUtils.isEmpty(password) -> {
                    binding.textInputPassword.error = "Please enter your password"
                    binding.textInputPassword.requestFocus()
                }
                password.length != 8-> {
                    binding.textInputPassword.error = "Password must be of 8  characters"
                    binding.textInputPassword.requestFocus()
                }
                else -> {


                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this,"Logged in Successfully", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

        }


    }
    /*

    override fun onStart() {
        super.onStart()

        if (this.firebaseAuth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
     */

    // Checks the user input  using validPassword() & set a helper text if something is incorrect
    private fun passwordFocusListener() {

        binding.password.setOnFocusChangeListener { _, focused ->

            if (focused) {
                binding.textInputPassword.helperText = validPassword()
            }

        }
    }

    // Checks if the input of the users are valid . If not it returns  a   text to passwordFocusListener
    private fun validPassword(): CharSequence? {

        val password = binding.password.text.toString()
        if (password.length != 8) {
            return "Password must be of 8  characters"
        }
        else {
            binding.textInputPassword.error = null

            return null
        }
    }


    // Checks the user input  using validEmail() & set a helper text if something is incorrect
    private fun emailFocusListener() {

        binding.email.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.textInputEmail.error = validEmail()
            }

        }
    }

    // Checks if the input of the users are valid . If not it returns  a  text to emailFocusListener
    private fun validEmail(): CharSequence? {

        val email = binding.email.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid Email.  Correct eg:-(a-z.0-9@gmail.com)"
        } else {
            binding.textInputEmail.error = null
            return null
        }
    }




}










