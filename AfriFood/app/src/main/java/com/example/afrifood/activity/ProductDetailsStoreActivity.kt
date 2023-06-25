package com.example.afrifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.afrifood.*
import com.example.afrifood.R
import com.example.afrifood.databinding.ActivityProductDetailsStoreBinding
import com.example.afrifood.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailsStoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailsStoreBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private companion object{
        //TAG
        const val TAG = "PRODUCT_DETAILS_TAG"
    }

    // will hold a boolean value false/true to indicate either is in current user's favorite list o not
    private var isInMyFavorite = false
    private var proId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsStoreBinding.inflate(layoutInflater)
        proId = intent.getStringExtra("id")!!
        getProductDetailsStore(proId)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")
        /*if(uid.isNotEmpty()){

            getUserData()
        }*/

        if(auth.currentUser != null){
            // user is logged in, check if product is in fav or not
            checkIsFavorite()
        }

        binding.cbHeart.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked){
                // Item added from Wishlist

                if(auth.currentUser == null){
                    // user not logged in cant do favorite functionality
                    Toast.makeText(this,"You're not logged in", Toast.LENGTH_SHORT).show()
                } else{
                    // user is logged in we can do favorite functionality
                    if (isInMyFavorite) {
                        MyApplication.removeFromFavorite(this, proId)
                    }
                    else{
                        Firebase.firestore.collection("products")
                            .document(proId).get().addOnSuccessListener {
                                val name = it.getString("productName")!!
                                val coverImg = it.getString("productCoverImg")!!

                                addToFavorite(proId, name, coverImg)
                            }
                    }

                }
            } else{
                // Item removed from Wishlist
                MyApplication.removeFromFavorite(this, proId)
            }
        }


        binding.textView26.setOnClickListener {

            Firebase.firestore.collection("products")
                .document(proId).get().addOnSuccessListener {
                    val name = it.getString("productName")!!
                    val coverImg = it.getString("productCoverImg")!!
                    val price = it.getString("productSp")!!

                    addToCart(proId, name, coverImg, price)
                }
        }


    }

    private fun addToCart(proId: String, name: String, coverImg: String, price: String) {
        val userCart = database.child(auth.uid!!).child("Cart").child(proId)
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
                            Toast.makeText(this@ProductDetailsStoreActivity, "Added to Cart", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this@ProductDetailsStoreActivity, "Failed to add to Cart due to ${e.message}", Toast.LENGTH_SHORT).show()

                            }

                    } else{
                    //if item not in cart, add new
                        val timestamp = System.currentTimeMillis()

                        //setup data to add in db
                        val hashMap  = HashMap<String, Any>()
                        hashMap["productId"] = proId
                        hashMap["name"] = name
                        hashMap["productType"] = "product"
                        hashMap["coverImg"] = coverImg
                        hashMap["timestamp"] = timestamp
                        hashMap["price"] = price
                        hashMap["quantity"] = "1".toInt()
                        hashMap["totalPrice"] = price.toFloat()

                        userCart.setValue(hashMap)
                            .addOnSuccessListener {
                                Toast.makeText(this@ProductDetailsStoreActivity, "Added to Cart", Toast.LENGTH_SHORT).show()

                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this@ProductDetailsStoreActivity, "Failed to add to Cart due to ${e.message}", Toast.LENGTH_SHORT).show()

                            }


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    /*private fun getUserData() {
        database.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    user = data.getValue(UserModel::class.java)!!
                }
                if (::id.isInitialized)
                    id = user.id!!
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductDetailsStoreActivity, "Failed to get User Profile data", Toast.LENGTH_SHORT).show()
            }

        })
    }*/


    // Room database function
    private fun getProductDetailsStore(proId: String?) {
        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImage") as ArrayList<String>
                val name = it.getString("productName")
                val Mrp = it.getString("productMrp")
                val productSp = it.getString("productSp")
                val productDesc = it.getString("productDescription")
                binding.textView22.text = name
                binding.textView23.text = "$Mrp g"
                binding.textView24.text = "$productSp €"
                binding.textView25.text = productDesc

                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.FIT))
                }

                //cartAction(proId, name, productSp, it.getString("productCoverImg"))





                binding.imageSlider.setImageList(slideList)
            }.addOnFailureListener {
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }



   /* private fun cartAction(proId: String, name: String?, productSp: String?, coverImg: String?) {

        val storeProductDao = StoreAppDatabase.getInstance(this).storeProductDao()
        if(storeProductDao.isExit(proId) != null){
            binding.textView26.text = "GO TO CART"
        }else{
            binding.textView26.text = "ADD TO CART"
        }
        binding.textView26.setOnClickListener {
            if(storeProductDao.isExit(proId) != null){
                openCart()
            }else{
                addToCart(storeProductDao, proId, name, productSp, coverImg)
            }
        }

    }

    private fun addToCart(
        storeProductDao: StoreProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverImg: String?
    ) {
        val data = StoreProductModel(proId, name, coverImg, productSp)
        lifecycleScope.launch(Dispatchers.IO){
            storeProductDao.insertProduct(data)
            binding.textView26.text = "GO TO CART"

        }

    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, NewActivity3::class.java))
        finish()
    } ---*/

    private fun checkIsFavorite(){
        Log.d(TAG, "checkIsFavorite:Checking if book is in fav or not")

        database.child(auth.uid!!).child("Favorites").child(proId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    isInMyFavorite = snapshot.exists()
                    if (isInMyFavorite){
                        // available in favorite
                            //set drawable icon
                        binding.cbHeart.setButtonDrawable(R.drawable.ic_heart_filled)
                    }
                    else{
                        // not available in favorite
                            //set drawable icon
                        binding.cbHeart.setButtonDrawable(R.drawable.ic_heart_outlined)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.cbHeart.setButtonDrawable(R.drawable.ic_heart_outlined)
                }

            })
    }

    private fun addToFavorite(proId: String, name: String, coverImg: String){

        Log.d(TAG, "addToFavorite: Adding to fav")
        val timestamp = System.currentTimeMillis()

        //setup data to add in db
        val hashMap = HashMap<String, Any>()
        hashMap["productId"] = proId
        hashMap["name"] = name
        hashMap["productType"] = "product"
        hashMap["coverImg"] = coverImg
        hashMap["timestamp"] = timestamp

        // save to db
        //val ref =FirebaseDatabase.getInstance().getReference("Users")
        database.child(auth.uid!!).child("Favorites").child(proId)
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