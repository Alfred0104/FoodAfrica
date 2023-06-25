package com.example.afrifood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afrifood.activity.FoodDetailsCountryActivity
import com.example.afrifood.activity.ProductDetailsStoreActivity
import com.example.afrifood.databinding.EachItemBinding
import com.example.afrifood.model.AddFoodModel

class FoodCountryAdapter(val context: Context, val list : ArrayList<AddFoodModel>)
    : RecyclerView.Adapter<FoodCountryAdapter.FoodCountryViewHolder>() {

    inner class FoodCountryViewHolder(val binding : EachItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCountryViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodCountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodCountryViewHolder, position: Int) {
        Glide.with(context).load(list[position].foodCoverImg).into(holder.binding.imageView12)

        holder.binding.textView13.text = list[position].foodName

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FoodDetailsCountryActivity::class.java)
            intent.putExtra("id",list[position].foodId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}