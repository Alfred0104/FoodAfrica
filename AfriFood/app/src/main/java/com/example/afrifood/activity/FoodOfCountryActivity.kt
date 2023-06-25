package com.example.afrifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.afrifood.R
import com.example.afrifood.adapter.FoodCountryAdapter
import com.example.afrifood.databinding.ActivityFoodOfCountryBinding
import com.example.afrifood.model.AddFoodModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FoodOfCountryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFoodOfCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodOfCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = intent.getStringExtra("ctry")

        binding.textView32.text = "Food from $textView"

        getFood(intent.getStringExtra("ctry"))
    }

    private fun getFood(countryElem: String?) {
        val list = ArrayList<AddFoodModel>()

        Firebase.firestore.collection("food").whereEqualTo("foodCategory", countryElem)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddFoodModel::class.java)
                    // qua sopra ho un piccolo dubbio secondo me serve mettere StoreModel
                    list.add(data!!)
                }
                //val recyclerView= view.findViewById<RecyclerView>(R.id.view)
                binding.view6.setHasFixedSize(true)
                binding.view6.layoutManager = GridLayoutManager(this, 2)
                binding.view6.adapter = FoodCountryAdapter(this, list)
            }
    }

}