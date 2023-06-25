package com.example.afrifood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.afrifood.databinding.FragmentAltroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AltroFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding : FragmentAltroBinding
    private lateinit var messageR : RelativeLayout
    private lateinit var auth : FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAltroBinding.inflate(layoutInflater)


        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            startActivity(Intent(context, MainActivity2::class.java))
        }


        // Welcome Username
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.textView43.text = "${snapshot.child("userName").value}"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        setHasOptionsMenu(true)


        messageR = binding.messageR
        messageR.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "fognoaubin086@gmail.com", null))
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearLogOut.setOnClickListener {
            val showPopUp = PopUpFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }
    }


}