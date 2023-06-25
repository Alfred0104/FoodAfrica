package com.example.afrifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.afrifood.adapter.FavAdapter
import com.example.afrifood.databinding.ActivityFavoriteBinding
import com.example.afrifood.model.FavModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoriteActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding : ActivityFavoriteBinding

    //  firebase auth
    private lateinit var auth: FirebaseAuth
    private lateinit var list : ArrayList<FavModel>
    private lateinit var favAdapter : FavAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        loadFavorite()
    }

    private fun loadFavorite(){

        //init arraylist
        list = ArrayList()
        binding.view7.layoutManager = GridLayoutManager(this, 2)

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).child("Favorites")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    // clear arraylist, before starting adding data
                    list.clear()
                    for (data in snapshot.children){

                        // get info in favorite adapter
                        val name = "${data.child("name").value}"
                        val coverImg = "${data.child("coverImg").value}"
                        val productId = "${data.child("productId").value}"
                        val productType = "${data.child("productType").value}"


                        //set model list
                        val model = FavModel()
                        model.name = name
                        model.coverImg = coverImg
                        model.productId = productId
                        model.productType = productType

                        //add model to list
                        list.add(model)
                    }


                    // setup adapter
                    favAdapter = FavAdapter(this@FavoriteActivity, list)
                    binding.view7.adapter = favAdapter
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }
}