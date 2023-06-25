package com.example.afrifood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afrifood.adapter.CountryAdapter
import com.example.afrifood.databinding.FragmentFoodMenuBinding
import com.example.afrifood.model.CountryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class foodMenuFragment : Fragment() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var binding: FragmentFoodMenuBinding

    //lateinit var namehp: TextView
    //private lateinit var recyclerView4: RecyclerView
    //private lateinit var foodList: ArrayList<Food>
    //private lateinit var foodAdapter: FoodAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodMenuBinding.inflate(layoutInflater)

        getCountry()
        return binding.root

    }

    private fun getCountry() {
        var list = ArrayList<CountryModel>()
        binding.view4.layoutManager = GridLayoutManager(context, 2)

        Firebase.firestore.collection("countries")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CountryModel::class.java)
                    list.add(data!!)

                }
                binding.view4.adapter= CountryAdapter(requireContext(), list)
            }
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //recyclerView2
        recyclerView4 = view.findViewById(R.id.view4)
        recyclerView4.setHasFixedSize(true)
        recyclerView4.layoutManager = GridLayoutManager(context, 2)
        foodList = ArrayList()

        addDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView4.adapter = foodAdapter

        foodAdapter.onItemClick = {
            val intent = Intent(context,countryActivity::class.java)
            startActivity(intent)
        }

    }


    private fun addDataToList(){
        foodList.add(Food(R.drawable.cameroun, "Camerun"))
        foodList.add(Food(R.drawable.cameroun, "Togo"))
        foodList.add(Food(R.drawable.cameroun, "Cote d'ivoire"))
        foodList.add(Food(R.drawable.cameroun, "Senegal"))
        foodList.add(Food(R.drawable.cameroun, "Burkina Faso"))
        foodList.add(Food(R.drawable.cameroun, "Gabon"))


        foodList.add(Food(R.drawable.cameroun, "Centrafrique"))
        foodList.add(Food(R.drawable.cameroun, "RDC"))
        foodList.add(Food(R.drawable.cameroun, "Afrique du Sud"))
        foodList.add(Food(R.drawable.cameroun, "Nigeria"))
        foodList.add(Food(R.drawable.cameroun, "Ghana"))
        foodList.add(Food(R.drawable.cameroun, "Madagascar"))
    }

*/

}