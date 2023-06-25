package com.example.afrifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afrifood.MainActivity2
import com.example.afrifood.NewActivity3
import com.example.afrifood.R
import com.example.afrifood.databinding.ActivityAdressBinding
import com.example.afrifood.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdressActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdressBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")


        if(auth.currentUser == null){
            startActivity(Intent(this, MainActivity2::class.java))
        }

        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.email.text.toString(),
                binding.userNumber.text.toString(),
                binding.userState.text.toString(),
                binding.userCity.text.toString(),
                binding.homeAddress.text.toString(),
                binding.userPinCode.text.toString()
            )
        }

    }

    private fun validateData(firstName : String, lastName : String, email : String, number : String, state : String,
                             city : String, homeAddress : String, pinCode : String) {

        if (state.isEmpty() || city.isEmpty() || homeAddress.isEmpty() || pinCode.isEmpty() || number.isEmpty()){
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            storeDataDelivery(firstName, lastName, email, number, state, city, homeAddress, pinCode)
        }

    }

    private fun storeDataDelivery(
        firstName: String,
        lastName: String,
        email: String,
        number: String,
        state: String,
        city: String,
        homeAddress: String,
        pinCode: String
    ) {


        database.child(auth.uid!!).child("Cart").removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this@AdressActivity, "Your order has been successfully sent", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, NewActivity3::class.java))
                        Toast.makeText(this@AdressActivity, "Continue your shopping", Toast.LENGTH_SHORT).show()


                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@AdressActivity, "Failed to send your order due to ${e.message}", Toast.LENGTH_SHORT).show()

                    }

    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.firstName.setText("${snapshot.child("firstName").value}")
                binding.lastName.setText("${snapshot.child("lastName").value}")
                binding.email.setText("${snapshot.child("email").value}")
                binding.userNumber.setText("${snapshot.child("cellulare").value}")
                binding.userState.setText("${snapshot.child("paese").value}")
                binding.userCity.setText("${snapshot.child("citta").value}")
                binding.homeAddress.setText("${snapshot.child("indResidenza").value}")
                binding.userPinCode.setText("${snapshot.child("cap").value}")
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}