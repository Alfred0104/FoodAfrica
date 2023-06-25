package com.example.afrifood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afrifood.activity.AdressActivity
import com.example.afrifood.adapter.MyCartAdapter
import com.example.afrifood.databinding.FragmentCartBinding
import com.example.afrifood.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding




    private lateinit var auth: FirebaseAuth
    private lateinit var list : ArrayList<MyCartModel>
    private lateinit var cartAdapter : MyCartAdapter

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        /* ---
        val preference = requireContext().getSharedPreferences("info",AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = StoreAppDatabase.getInstance(requireContext()).storeProductDao()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)

            totalCost(it)
        } --- */

        auth = FirebaseAuth.getInstance()

        loadCartFromFirebase()

        binding.Checkout.setOnClickListener {
            startActivity(Intent(context, AdressActivity::class.java))
        }


        return binding.root
    }

    private fun loadCartFromFirebase() {
        //init arraylist
        list = ArrayList()
        binding.cartRecycler.layoutManager = LinearLayoutManager(context)

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).child("Cart")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // clear arraylist, before starting adding data
                    list.clear()
                    for (data in snapshot.children) {

                        // get info in MyCartAdapter adapter
                        val name = "${data.child("name").value}"
                        val coverImg = "${data.child("coverImg").value}"
                        val productId = "${data.child("productId").value}"
                        val productType = "${data.child("productType").value}"
                        val price = "${data.child("price").value}"
                        val quantity = "${data.child("quantity").value}"
                        val totalPrice = "${data.child("totalPrice").value}"


                        //set model list
                        val model = MyCartModel()
                        model.name = name
                        model.coverImg = coverImg
                        model.productId = productId
                        model.productType = productType
                        model.price = price
                        var number = 0 // or any appllication default value

                        try {
                            number = quantity.toInt()
                        } catch (nfe: NumberFormatException) {
                            nfe.printStackTrace()
                        }
                        model.quantity = number

                        var number2 = 0f // or any appllication default value

                        try {
                            number2 = totalPrice.toFloat()
                        } catch (nfe: NumberFormatException) {
                            nfe.printStackTrace()
                        }
                        model.totalPrice = number2
                        //add model to list
                        list.add(model)
                    }

                    var sum = 0.0
                    var totItem = 0
                    for (model in list){
                        sum += model.totalPrice
                        totItem += model.quantity
                    }
                    binding.textView29.text = "Total Item in Cart is ${totItem}"

                    binding.textView30.text = "Total Cost : ${sum} €"

                    // setup adapter
                    cartAdapter = MyCartAdapter(view!!.context, list)
                    binding.cartRecycler.adapter = cartAdapter

                }


                override fun onCancelled(error: DatabaseError) {

                }

            })
    }



    /* --- Room database
    private fun totalCost(data: List<StoreProductModel>?) {
        var total = 0
        for (item in data!!){
            total += item.productSp!!.toInt()
        }

        binding.textView29.text = "Total Item in Cart is ${data.size}"
        binding.textView30.text = "Total Cost $total €"

        binding.Checkout.setOnClickListener {
            if (data.isNotEmpty()) {
                val intent = Intent(context, AdressActivity::class.java)
                intent.putExtra("totalCost", total)
                startActivity(intent)
            }
        }
    } --- */


}

/*fun totalCost(data: List<StoreProductModel>?) {
       var total = 0
       for (item in data!!){
           total += item.productSp!!.toInt()
       }

       binding.textView29.text = "Total item in cart is ${data.size}"
       binding.textView30.text = "Total Cost : $total €"

       binding.Checkout.setOnClickListener {
           Toast.makeText(context,"You order have been completed", Toast.LENGTH_SHORT).show()

           startActivity(Intent(requireContext(), NewActivity3::class.java))

       }

   }*/