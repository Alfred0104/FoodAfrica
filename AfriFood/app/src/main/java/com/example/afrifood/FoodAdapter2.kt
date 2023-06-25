package com.example.afrifood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter2(private val foodList : List<Food>) :
    RecyclerView.Adapter<FoodAdapter2.FoodViewHolder>() {
    class FoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val foodImageView: ImageView = itemView.findViewById(R.id.imageView12)
        val foodNameTv : TextView = itemView.findViewById(R.id.textView13)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item2, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodImageView.setImageResource(food.foodImage)
        holder.foodNameTv.text = food.foodName
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}