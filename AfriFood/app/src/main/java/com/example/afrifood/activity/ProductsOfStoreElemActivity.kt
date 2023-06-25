package com.example.afrifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afrifood.R
import com.example.afrifood.adapter.StoreProductAdapter
import com.example.afrifood.databinding.ActivityProductDetailsStoreBinding
import com.example.afrifood.databinding.ActivityProductsOfStoreElemBinding
import com.example.afrifood.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.parcelize.Parcelize
//import kotlinx.android.synthetic.main.activity_products_of_store_elem.*


class ProductsOfStoreElemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductsOfStoreElemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsOfStoreElemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val textView = intent.getStringExtra("store")

        binding.textView.text = "$textView"

        getProducts(intent.getStringExtra("store"))
    }



    private fun getProducts(storeElem: String?) {
        val list = ArrayList<AddProductModel>()

        Firebase.firestore.collection("products").whereEqualTo("productCategory", storeElem)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    // qua sopra ho un piccolo dubbio secondo me serve mettere StoreModel
                    list.add(data!!)
                }
                //val recyclerView= view.findViewById<RecyclerView>(R.id.view)
                binding.view.setHasFixedSize(true)
                binding.view.layoutManager = GridLayoutManager(this, 2)
                binding.view.adapter = StoreProductAdapter(this, list)
            }
    }
}