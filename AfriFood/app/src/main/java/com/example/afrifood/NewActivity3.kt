package com.example.afrifood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
//import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
//import kotlinx.android.synthetic.main.toolbar.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.afrifood.activity.FavoriteActivity
import com.example.afrifood.databinding.ActivityNew3Binding
//import com.example.loginpageui.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//import kotlinx.android.synthetic.main.activity_new3.*


class NewActivity3: AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    //private lateinit var  bottomNavView : BottomNavigationView
    private lateinit var  binding: ActivityNew3Binding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNew3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("Users")


       

        /*toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        //toolbar!!.title = "Ciao Emma"
        val action=supportActionBar
        action!!.title="Ciao Emma"*/



       val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
       val fragmentContainer = findViewById<View>(R.id.fragmentContainer)
       bottomNav.setupWithNavController(fragmentContainer.findNavController())







            /*supportActionBar?.hide()
            bottomNavView = binding.root.bottomNavigationView

            val HomeFragment = HomeFragment()
            val foodMenuFragment = foodMenuFragment()

            setThatFragment(HomeFragment)

            bottomNavView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.ic_home -> {
                        setThatFragment(HomeFragment)
                    }

                    R.id.ic_foodMenu -> {
                        setThatFragment(foodMenuFragment)
                    }
                }
                true
            }*/

    }




    /*private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)

        //val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu!!.findItem(R.id.nav_search)
        val searchView = searchItem?.actionView as SearchView
        //namehp = findViewById(R.id.textView13)
        /*val favoriteItem = menu.findItem(R.id.nav_favorite)
        val favoriteView = favoriteItem?.actionView
        favoriteView?.setOnClickListener {
            startActivity(Intent(this@NewActivity3,FavoriteActivity::class.java))
        }*/







        //searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

       /* searchView.setOnSearchClickListener {
            if(namehp?.isVisible===true)
                namehp.visibility= View.GONE
            else
                namehp.visibility= View.VISIBLE
        }*/

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{


            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()

                searchView.setQuery("",false)
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val favoriteItem = menu.findItem(R.id.nav_favorite)

                favoriteItem?.isVisible = newText == ""
                return false
            }
        })
        return true
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }



}

/*
lateinit var toolbar: androidx.appcompat.widget.Toolbar
private lateinit var binding : ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    replaceFragment(HomeFragment())

    toolbar = findViewById(R.id.toolbar2)
    setSupportActionBar(toolbar)
    //toolbar!!.title = "Ciao Emma"
    var action=supportActionBar
    action!!.title="Ciao Emma"


    binding.root.bottomNavigationView.setOnItemSelectedListener {

         when(it.itemId){
             R.id.ic_home -> replaceFragment(HomeFragment())
             R.id.ic_foodMenu -> replaceFragment(foodMenuFragment())

             else ->{


             }
         }
         true
     }

}*/