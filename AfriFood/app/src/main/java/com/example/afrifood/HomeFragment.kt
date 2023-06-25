package com.example.afrifood

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afrifood.activity.FavoriteActivity
import com.example.afrifood.adapter.CountryAdapter
import com.example.afrifood.adapter.FavAdapter
import com.example.afrifood.adapter.FoodCountryAdapter
import com.example.afrifood.adapter.StoreProductAdapter
import com.example.afrifood.databinding.FragmentHomeBinding
import com.example.afrifood.model.AddFoodModel
import com.example.afrifood.model.AddProductModel
import com.example.afrifood.model.CountryModel
import com.example.afrifood.model.FavModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth : FirebaseAuth
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
        binding = FragmentHomeBinding.inflate(layoutInflater)


        auth = FirebaseAuth.getInstance()


        // Welcome Username
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                        binding.textView42.text = "Welcome ${snapshot.child("userName").value}"
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



        binding.cbHeart3.setOnClickListener {
            startActivity(Intent(context, FavoriteActivity::class.java))
        }

        getCategories()

        getProducts()
        return binding.root

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if (preference.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

    }

    private fun getProducts() {
        val list1 = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list1.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list1.add(data!!)
                }
                binding.view2.adapter = StoreProductAdapter(requireContext(), list1)
            }

    }

    private fun getCategories() {
        val list = ArrayList<CountryModel>()
        Firebase.firestore.collection("countries")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CountryModel::class.java)
                    list.add(data!!)
                }
                binding.view1.adapter = CountryAdapter(requireContext(), list)
            }
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

        /* --//recyclerView3
        recyclerView3 = view.findViewById(R.id.view3)
        recyclerView3.setHasFixedSize(true)
        recyclerView3.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        foodList = ArrayList()

        /*val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        foodList = ArrayList()*/

        addDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView3.adapter = foodAdapter -- */
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
    } -- */

}