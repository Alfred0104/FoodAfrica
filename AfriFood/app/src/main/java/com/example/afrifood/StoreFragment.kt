package com.example.afrifood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.afrifood.adapter.StoreAdapter
import com.example.afrifood.databinding.FragmentStoreBinding
import com.example.afrifood.model.StoreModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StoreFragment : Fragment() {
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var binding: FragmentStoreBinding
    //lateinit var namehp: TextView
    //private lateinit var recyclerView5: RecyclerView
    //private lateinit var foodList: ArrayList<Food>
    //private lateinit var foodAdapter: FoodAdapter2



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(layoutInflater)

        getElemStore()
        return binding.root
    }

    private fun getElemStore() {
        var list = ArrayList<StoreModel>()
        binding.view5.layoutManager = GridLayoutManager(context, 1)

        Firebase.firestore.collection("storeElements")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(StoreModel::class.java)
                    list.add(data!!)

                }
                binding.view5.adapter= StoreAdapter(requireContext(), list)
            }
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //recyclerView2
        recyclerView5 = view.findViewById(R.id.view5)
        recyclerView5.setHasFixedSize(true)
        recyclerView5.layoutManager = GridLayoutManager(context, 1)
        foodList = ArrayList()

        addDataToList()

        foodAdapter = FoodAdapter2(foodList)
        recyclerView5.adapter = foodAdapter

    }


    private fun addDataToList(){
        foodList.add(Food(R.drawable.cameroun, "Legumes"))
        foodList.add(Food(R.drawable.cameroun, "Poissons et Viandes"))
        foodList.add(Food(R.drawable.cameroun, "Epices"))
        foodList.add(Food(R.drawable.cameroun, "Féculents"))
        foodList.add(Food(R.drawable.cameroun, "Tubercules"))
        foodList.add(Food(R.drawable.cameroun, "Autres"))

    }
*/

}