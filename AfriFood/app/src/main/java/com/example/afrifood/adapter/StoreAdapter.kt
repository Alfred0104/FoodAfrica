package com.example.afrifood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.afrifood.activity.ProductsOfStoreElemActivity
import com.example.afrifood.R
import com.example.afrifood.databinding.EachItem2Binding
import com.example.afrifood.model.StoreModel


class StoreAdapter(var context : Context, val list : ArrayList<StoreModel>) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    inner class  StoreViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = EachItem2Binding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.each_item2, parent, false))
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.binding.textView13.text = list[position].store
        Glide.with(context).load(list[position].img).into(holder.binding.imageView12)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ProductsOfStoreElemActivity::class.java)
            intent.putExtra("store", list[position].store)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}