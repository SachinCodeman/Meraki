package com.example.meraki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.meraki.databinding.ActivityForgrtPassBinding
import com.example.meraki.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class ForgrtPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgrtPassBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgrtPassBinding.inflate(layoutInflater)
        setContentView(binding.root)



        firebaseAuth = FirebaseAuth.getInstance()

        emailFocusListener()

        binding.alreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.signupReference.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgetpassbtn.setOnClickListener {
            validateData()
        }

    }

    // Checks if the email is empty
    private fun validateData() {

        email = binding.etEmail.text.toString()

        if (email.isEmpty()){
            binding.forgetInputLayout.error = "Please enter your email"
        }
        else{
            binding.forgetInputLayout.error = null
            forgetPass()
        }


    }

    // Creating new password & sending it on user email
    private fun forgetPass() {

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this,"Password is sent on your email ",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
    }






    }




    // Checks the user input  using validEmail() & set a helper text if something is incorrect
    private fun emailFocusListener() {

        binding.etEmail.setOnFocusChangeListener { _, focused ->

            if (!focused) {
                binding.forgetInputLayout.error = validEmail()
            }

        }
    }


    // Checks if the input of the users are valid . If not it returns  a  text to emailFocusListener
    private fun validEmail(): CharSequence? {

        val email = binding.etEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid Email.  Correct eg:-(a-z.0-9@gmail.com)"
        }
        else{
            binding.forgetInputLayout.error = null
            return null
        }
    }




}