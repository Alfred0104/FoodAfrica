package com.example.afrifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.afrifood.MyApplication
import com.example.afrifood.NewActivity3
import com.example.afrifood.R
import com.example.afrifood.databinding.ActivityFoodDetailsCountryBinding
import com.example.afrifood.model.MyCartModel
import com.example.afrifood.roomdb.StoreAppDatabase
import com.example.afrifood.roomdb.StoreProductDao
import com.example.afrifood.roomdb.StoreProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodDetailsCountryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFoodDetailsCountryBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private companion object{
        //TAG
        const val TAG = "PRODUCT_DETAILS_TAG"
    }

    // will hold a boolean value false/true to indicate either is in current user's favorite list o not
    private var isInMyFavorite = false
    private var fdId = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsCountryBinding.inflate(layoutInflater)

        fdId = intent.getStringExtra("id")!!
        getFoodDetailsCountry(fdId)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")

        if(auth.currentUser != null){
            // user is logged in, check if product is in fav or not
            checkIsFavorite()
        }


        binding.cbHeart1.setOnCheckedChangeListener { checkBox, isChecked ->

            if(isChecked){// Item added from Wishlist

                if(auth.currentUser == null){
                    // user not logged in cant do favorite functionality
                    Toast.makeText(this,"You're not logged in", Toast.LENGTH_SHORT).show()
                } else{
                    // user is logged in we can do favorite functionality
                    if (isInMyFavorite) {
                        MyApplication.removeFromFavorite(this, fdId)
                    }
                    else{
                        Firebase.firestore.collection("food")
                            .document(fdId).get().addOnSuccessListener {
                                val name = it.getString("foodName")!!
                                val coverImg = it.getString("foodCoverImg")!!

                                addToFavorite(fdId, name, coverImg)
                            }
                    }

                }
            } else{
                MyApplication.removeFromFavorite(this, fdId)
            }
        }

        binding.textView36.setOnClickListener {

            Firebase.firestore.collection("food")
                .document(fdId).get().addOnSuccessListener {
                    val name = it.getString("foodName")!!
                    val coverImg = it.getString("foodCoverImg")!!
                    val price = it.getString("foodSp")!!

                    addToCart(fdId, name, coverImg, price)
                }
        }



    }


    private fun addToCart(proId: String, name: String, coverImg: String, price: String) {
        val userCart = database.child(auth.uid!!).child("Cart").child(fdId)
        userCart.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    //if Item already in cart just update

                    val myCartModel = snapshot.getValue(MyCartModel::class.java)
                    val updateData : HashMap<String, Any> = HashMap()
                    myCartModel!!.quantity = myCartModel!!.quantity + 1

                    updateData["quantity"] = myCartModel!!.quantity
                    updateData["totalPrice"] = myCartModel!!.quantity * myCartModel.price!!.toFloat()

                    userCart.updateChildren(updateData).addOnSuccessListener {
                        Toast.makeText(this@FoodDetailsCountryActivity, "Added to Cart", Toast.LENGTH_SHORT).show()
                    }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@FoodDetailsCountryActivity, "Failed to add to Cart due to ${e.message}", Toast.LENGTH_SHORT).show()

                        }

                } else{
                    //if item not in cart, add new
                    val timestamp = System.currentTimeMillis()

                    //setup data to add in db
                    val hashMap  = HashMap<String, Any>()
                    hashMap["productId"] = proId
                    hashMap["name"] = name
                    hashMap["productType"] = "food"
                    hashMap["coverImg"] = coverImg
                    hashMap["timestamp"] = timestamp
                    hashMap["price"] = price
                    hashMap["quantity"] = "1".toInt()
                    hashMap["totalPrice"] = price.toFloat()

                    userCart.setValue(hashMap)
                        .addOnSuccessListener {
                            Toast.makeText(this@FoodDetailsCountryActivity, "Added to Cart", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@FoodDetailsCountryActivity, "Failed to add to Cart due to ${e.message}", Toast.LENGTH_SHORT).show()

                        }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getFoodDetailsCountry(fdId: String?) {
        Firebase.firestore.collection("food")
            .document(fdId!!).get().addOnSuccessListener {
                val list = it.get("foodImage") as ArrayList<String>
                val name = it.getString("foodName")
                val foodSp = it.getString("foodSp")
                val foodDesc = it.getString("foodDescription")
                binding.textView33.text = name
                binding.textView34.text = "$foodSp €"
                binding.textView35.text = foodDesc

                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.FIT))
                }

                //cartAction(fdId, name, foodSp, it.getString("foodCoverImg"))

                binding.imageSlider.setImageList(slideList)
            }.addOnFailureListener {
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    /*---- Room database implementation

    private fun cartAction(fdId: String, name: String?, foodSp: String?, coverImg: String?) {
        val storeProductDao = StoreAppDatabase.getInstance(this).storeProductDao()
        if(storeProductDao.isExit(fdId) != null){
            binding.textView36.text = "GO TO CART"
        }else{
            binding.textView36.text = "ADD TO CART"
        }
        binding.textView36.setOnClickListener {
            if(storeProductDao.isExit(fdId) != null){
                openCart()
            }else{
                addToCart(storeProductDao, fdId, name, foodSp, coverImg)
            }
        }
    }

    private fun addToCart(
        storeProductDao: StoreProductDao,
        fdId: String,
        name: String?,
        foodSp: String?,
        coverImg: String?
    ) {
        val data = StoreProductModel(fdId, name, coverImg, foodSp)
        lifecycleScope.launch(Dispatchers.IO){
            storeProductDao.insertProduct(data)
            binding.textView36.text = "GO TO CART"

        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, NewActivity3::class.java))
        finish()
    }

    ----*/


    private fun checkIsFavorite(){
        Log.d(TAG, "checkIsFavorite:Checking if book is in fav or not")

        database.child(auth.uid!!).child("Favorites").child(fdId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isInMyFavorite = snapshot.exists()
                    if (isInMyFavorite){
                        // available in favorite
                        //set drawable icon
                        binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_filled)
                    }
                    else{
                        // not available in favorite
                        //set drawable icon
                        binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_outlined)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_outlined)
                }

            })
    }

    private fun addToFavorite(fdId: String, name: String, coverImg: String){

        Log.d(TAG, "addToFavorite: Adding to fav")
        val timestamp = System.currentTimeMillis()

        //setup data to add in db
        val hashMap = HashMap<String, Any>()
        hashMap["productId"] = fdId
        hashMap["name"] = name
        hashMap["productType"] = "food"
        hashMap["coverImg"] = coverImg
        hashMap["timestamp"] = timestamp

        // save to db
        //val ref =FirebaseDatabase.getInstance().getReference("Users")
        database.child(auth.uid!!).child("Favorites").child(fdId)
            .setValue(hashMap)
            .addOnSuccessListener {
                //added to fav
                Log.d(TAG, "addToFavorite: Added to fav")
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                //failed to add to fav
                Log.d(TAG, "addToFavorite: Failed to add to fav due to ${e.message}")
                Toast.makeText(this, "Failed to add to fav due to ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }


}