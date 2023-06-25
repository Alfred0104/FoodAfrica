package com.example.afrifood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afrifood.activity.ProductDetailsStoreActivity
import com.example.afrifood.databinding.EachItemBinding
import com.example.afrifood.model.AddProductModel

class StoreProductAdapter (val context: Context, val list : ArrayList<AddProductModel>)
    : RecyclerView.Adapter<StoreProductAdapter.StoreProductViewHolder>(){

    inner class StoreProductViewHolder(val binding : EachItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreProductViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return StoreProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView12)

        holder.binding.textView13.text = list[position].productName

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsStoreActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}