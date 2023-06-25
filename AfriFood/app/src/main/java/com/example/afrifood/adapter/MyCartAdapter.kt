package com.example.afrifood.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afrifood.activity.FoodDetailsCountryActivity
import com.example.afrifood.activity.ProductDetailsStoreActivity
import com.example.afrifood.databinding.LayoutCartItemBinding
import com.example.afrifood.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyCartAdapter : RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    // Context
    private val context : Context

    // ArrayList to hold all products in my cart
    private var productArrayList : ArrayList<MyCartModel>

    // View binding
    private lateinit var binding : LayoutCartItemBinding

    // Firebase authetication

    private lateinit var auth : FirebaseAuth

    // Constructor
    constructor(context: Context, productArrayList: ArrayList<MyCartModel>) {
        this.context = context
        this.productArrayList = productArrayList
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyCartViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val model = productArrayList[position]
        holder.pName.text = model.name
        holder.txtPrice.text = "${model.price} €"

        Glide.with(context).load(model.coverImg).into(holder.coverImg)

        auth = FirebaseAuth.getInstance()

        //Events
        holder.btnMinus.setOnClickListener {_ -> minusCartItem(holder, model)}
        holder.btnAdd.setOnClickListener {_ -> plusCartItem(holder, model)}
        holder.txtQuantity.text = "${model.quantity}"
        holder.removeCartBtn.setOnClickListener { _ ->

            FirebaseDatabase.getInstance().getReference("Users")
                .child(auth.uid!!).child("Cart")
                .child(model.productId!!).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Removed from Cart", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to remove from cart due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

        //handle click, open product/food details page, pass product/food id to load details
        holder.itemView.setOnClickListener {
            if (model.productType == "product"){
                val intent = Intent(context, ProductDetailsStoreActivity::class.java)
                intent.putExtra("id", model.productId)!!
                context.startActivity(intent)

            }else if (model.productType == "food"){
                val intent = Intent(context, FoodDetailsCountryActivity::class.java)
                intent.putExtra("id", model.productId)!!
                context.startActivity(intent)
            }
        }
    }

    private fun plusCartItem(holder: MyCartAdapter.MyCartViewHolder, model: MyCartModel) {

        var number2 = 0f // or any appllication default value

        try {
            number2 = model.price!!.toFloat()
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }

        model.quantity += 1
        model.totalPrice = model.quantity * number2
        val id = model.productId
        holder.txtQuantity.text = "${model.quantity}"
        updateFirebase(id, model)

    }

    private fun minusCartItem(holder: MyCartAdapter.MyCartViewHolder, model: MyCartModel) {

        if(model.quantity > 1)
        {

            var number2 = 0f // or any appllication default value

            try {
                number2 = model.price!!.toFloat()
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }

            model.quantity -= 1
            model.totalPrice = model.quantity * number2
            val id = model.productId
            holder.txtQuantity.text = "${model.quantity}"
            updateFirebase(id ,model)

        }
    }

    private fun updateFirebase(id : String?, model: MyCartModel) {


        FirebaseDatabase.getInstance().getReference("Users")
            .child(auth.uid!!).child("Cart")
            .child(id!!).setValue(model)


    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }


    /*View holder class to manage UI views of layout_cart_item.xml */
    inner class MyCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var pName = binding.textView27
        var txtPrice = binding.textView28
        var txtQuantity = binding.txtQuantity
        var coverImg = binding.imageView13
        var removeCartBtn = binding.imageView14
        var btnMinus = binding.btnMinus
        var btnAdd = binding.btnAdd

    }


}