package com.example.afrifood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afrifood.MyApplication
import com.example.afrifood.activity.FoodDetailsCountryActivity
import com.example.afrifood.activity.ProductDetailsStoreActivity
import com.example.afrifood.databinding.LayoutFavItemBinding
import com.example.afrifood.model.FavModel

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavViewHolder>{

    // Context
    private val context : Context

    // ArrayList to hold all products in my fav
    private var productArrayList : ArrayList<FavModel>

    // View binding
    private lateinit var binding : LayoutFavItemBinding

    // Constructor
    constructor(context: Context, productArrayList: ArrayList<FavModel>) {
        this.context = context
        this.productArrayList = productArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        // bind/inflate layout_fav_item.xml
        binding = LayoutFavItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val model = productArrayList[position]
        holder.pName.text = model.name
        Glide.with(context).load(model.coverImg).into(holder.coverImg)
        val productType = model.productType
        model.isFavorite = true

        //handle click, open product/food details page, pass product/food id to load details
        holder.itemView.setOnClickListener {
            if (productType == "product"){
                val intent = Intent(context, ProductDetailsStoreActivity::class.java)
                intent.putExtra("id", model.productId)!!
                context.startActivity(intent)

            }else if (model.productType == "food"){
                val intent = Intent(context, FoodDetailsCountryActivity::class.java)
                intent.putExtra("id", model.productId)!!
                context.startActivity(intent)
            }
        }

        // handle click, remove from favorite
        holder.removeFavBtn.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked){
                MyApplication.removeFromFavorite(context, model.productId)
            }

        }


    }

    override fun getItemCount(): Int {
        return productArrayList.size // return size of list / number of items in list
    }


    /*View holder class to manage UI views of layout_fav_item.xml */
    inner class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var coverImg = binding.imageView15
        var pName = binding.textView41
        var removeFavBtn = binding.cbHeart2
    }


}