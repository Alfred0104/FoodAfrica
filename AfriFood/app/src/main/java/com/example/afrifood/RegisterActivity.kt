package com.example.afrifood

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.afrifood.databinding.ActivityRegisterBinding
import com.example.afrifood.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable


@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private var name = ""
    private var email = ""
    private var password = ""
    private var firstName = ""
    private var lastName = ""
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

// Database RealTime storage


// Auth
        auth = FirebaseAuth.getInstance()

// Firstname Validation
        val firstNameStream = RxTextView.textChanges(binding.etFirstname)
            .skipInitialValue()
            .map { firstName ->
                firstName.isEmpty()
            }
        firstNameStream.subscribe {
            showFirstNameExistAlert(it)
        }

// Lastname Validation
        val lastNameStream = RxTextView.textChanges(binding.etLastname)
            .skipInitialValue()
            .map { lastName ->
                lastName.isEmpty()
            }
        lastNameStream.subscribe {
            showLastNameExistAlert(it)
        }

// Email Validation
        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }

// Username Validation
        val usernameStream = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map{ username ->
                username.length < 6
            }
        usernameStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }


// Password Validation
        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map{ password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }

// Confirm Password Validation
        val passwordConfirmStream = Observable.merge(
            RxTextView.textChanges(binding.etPassword)
                .skipInitialValue()
                .map{ password ->
                    password.toString() != binding.etConfirmPassword.text.toString()
                },
            RxTextView.textChanges(binding.etConfirmPassword)
                .skipInitialValue()
                .map{ confirmPassword ->
                    confirmPassword.toString() != binding.etPassword.text.toString()

                })
        passwordConfirmStream.subscribe {
                showPasswordConfirmAlert(it)
            }

//Button Enable True o False
        val invalidFieldStream = Observable.combineLatest(

            lastNameStream,
            emailStream,
            usernameStream,
            passwordStream,
            passwordConfirmStream,
            {lastNameInvalid : Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmInvalid: Boolean ->
                !lastNameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid && !passwordConfirmInvalid
            })
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnRegister.isEnabled = true
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary_color)
            } else{
                binding.btnRegister.isEnabled = false
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.darker_gray)
            }
        }
// Click

        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.btnRegister.setOnClickListener {


            firstName = binding.etFirstname.text.toString()
            lastName = binding.etLastname.text.toString()
            userName = binding.etUsername.text.toString()
            email = binding.etEmail.text.toString().trim()
            password = binding.etPassword.text.toString().trim()


            // For Authentication
            registerUser()



        }
        binding.tvHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }





    private fun showFirstNameExistAlert(isNotValid: Boolean){
        binding.etFirstname.error = if (isNotValid) "Insert Your FirstName !" else null
    }

    private fun showLastNameExistAlert(isNotValid: Boolean){
        binding.etLastname.error = if (isNotValid) "Insert Your LaststName !" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String){
        if(text == "Username")
            binding.etUsername.error = if(isNotValid) "$text will have more than 6 caractere!" else null
        else if (text == "Password")
            binding.etPassword.error = if(isNotValid) "$text will have more than 6 caractere!" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean){
        binding.etEmail.error = if(isNotValid) "Invalid Email " else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean){
        binding.etConfirmPassword.error = if(isNotValid) "Password don't match!" else null
    }

    private fun registerUser() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    sendData()



                } else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendData() {
        // Database RealTime storage
            //timestamp
            val timestamp = System.currentTimeMillis()

            //get current user uid, since user is registered so we can get it now
            val uid = auth.uid

            //setup data to add in db

            val hashMap : HashMap<String, Any?> = HashMap()
            hashMap["uid"] = uid
            hashMap["firstName"] = firstName
            hashMap["lastName"] = lastName
            hashMap["userName"] = userName
            hashMap["userType"] = "user"
            hashMap["email"] = email
            hashMap["timestamp"] = timestamp
            hashMap["profileImg"] = ""
            hashMap["sesso"] = ""
            hashMap["CF"] = ""
            hashMap["dataN"] = ""
            hashMap["luogoN"] = ""
            hashMap["cittadinanza"] = ""
            hashMap["indResidenza"] = ""
            hashMap["provResidenza"] = ""
            hashMap["coResidenza"] = ""
            hashMap["CAP"] = ""
            hashMap["cellulare"] = ""
            hashMap["citta"] = ""
            hashMap["paese"] = ""

            // set data to db
            database.child(uid!!)
                     .setValue(hashMap)
                     .addOnSuccessListener {
                         Toast.makeText(this, "Registration Successed", Toast.LENGTH_SHORT).show()
                         startActivity(Intent(this, LoginActivity::class.java))

                     }
                     .addOnFailureListener { e ->
                         Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
                     }
    }
}