package com.example.fivecontactsvlvkotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.fivecontactsvlvkotlin.databinding.ActivitySignUpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser




class SignUPActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpactivityBinding

    private  lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog


    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""
    private var logado = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Sing Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account in...")
        progressDialog.setCanceledOnTouchOutside(false)


        firebaseAuth = FirebaseAuth.getInstance()


        binding.checkBoxLogar.setOnClickListener(View.OnClickListener {
            if (binding.checkBoxLogar.isChecked) {
                //CLICADO
                logado = true
            } else {
                //NAO CLICADO
                logado = false
            }
        })




        binding.singUpBtn.setOnClickListener {
            validateData()
        }





    }


    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Please enter password"
        }
        else if(password.length < 6){
            binding.passwordEt.error = "Password must atleast 6 cheacters long"
        }
        else{
            firebaseSingUp()
        }

    }

    private fun firebaseSingUp() {
        progressDialog.show()


        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email $email", Toast.LENGTH_SHORT).show()
                // Colocar o nome do firebase
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()


            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}