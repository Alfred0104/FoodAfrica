package com.example.afrifood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afrifood.R
import com.example.afrifood.activity.FoodOfCountryActivity
import com.example.afrifood.activity.ProductsOfStoreElemActivity
import com.example.afrifood.databinding.EachItemBinding
import com.example.afrifood.model.CountryModel

class CountryAdapter(var context: Context, val list : ArrayList<CountryModel>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding = EachItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
     return CountryViewHolder(LayoutInflater.from(context).inflate(R.layout.each_item, parent, false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.textView13.text= list[position].ctry
        Glide.with(context).load(list[position].img).into(holder.binding.imageView12)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, FoodOfCountryActivity::class.java)
            intent.putExtra("ctry", list[position].ctry)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}