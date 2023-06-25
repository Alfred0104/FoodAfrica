package com.example.afrifood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class home2Fragment : Fragment() {


    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    //lateinit var namehp: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var foodList: ArrayList<Food>
    private lateinit var foodAdapter: FoodAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    /* --override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerView2
        recyclerView = view.findViewById(R.id.view1)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        foodList = ArrayList()

        addDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView.adapter = foodAdapter

        //recyclerView2
        recyclerView2 = view.findViewById(R.id.view2)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        foodList = ArrayList()

        /*val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        foodList = ArrayList()*/

        addDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView2.adapter = foodAdapter

        //recyclerView3
        recyclerView3 = view.findViewById(R.id.view3)
        recyclerView3.setHasFixedSize(true)
        recyclerView3.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        foodList = ArrayList()

        /*val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        foodList = ArrayList()*/

        addDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView3.adapter = foodAdapter
    }


    private fun addDataToList(){
        foodList.add(Food(R.drawable.cameroun, "koki"))
        foodList.add(Food(R.drawable.cameroun, "pistache"))
        foodList.add(Food(R.drawable.cameroun, "pilé de pomme"))
        foodList.add(Food(R.drawable.cameroun, "atchiequié"))
        foodList.add(Food(R.drawable.cameroun, "mbongo"))
        foodList.add(Food(R.drawable.cameroun, "taro"))


        foodList.add(Food(R.drawable.cameroun, "koki"))
        foodList.add(Food(R.drawable.cameroun, "pistache"))
        foodList.add(Food(R.drawable.cameroun, "pilé de pomme"))
        foodList.add(Food(R.drawable.cameroun, "atchiequié"))
        foodList.add(Food(R.drawable.cameroun, "mbongo"))
        foodList.add(Food(R.drawable.cameroun, "taro"))
    } --*/

}