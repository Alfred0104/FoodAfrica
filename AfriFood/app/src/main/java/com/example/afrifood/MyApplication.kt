package com.example.afrifood

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.afrifood.activity.ProductDetailsStoreActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object{

        public fun removeFromFavorite(context: Context, proId: String){
            val TAG = "REMOVE_FAV_TAG"
            Log.d(TAG, "removeFromFavorite: Removing to fav")

            val auth = FirebaseAuth.getInstance()
            //database ref
            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(auth.uid!!).child("Favorites").child(proId)
                .removeValue()
                .addOnSuccessListener {
                    Log.d(TAG, "removeFromFavorite: Removed to fav")
                    Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "removeFromFavorite: Failed to remove from fav due to ${e.message}")
                    Toast.makeText(context, "Failed to remove from fav due to ${e.message}", Toast.LENGTH_SHORT).show()


                }
        }

    }
}