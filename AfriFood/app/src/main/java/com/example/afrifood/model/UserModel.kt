package com.example.afrifood.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class UserModel(
    val firstName : String? = "",
    val lastName : String? = "",
    val userName : String? = "",
    val email: String? = "",
    val id: String? = "",
    val sesso : String? = "",
    val CF : String? = "",
    val dateN : String? = "",
    val LuogoN : String? = "",
    val cittadinanza : String? = "",
    val indResidenza : String? = "",
    val provResidenza : String? = "",
    val coResidenza : String? = "",
    val numCiv : String? = "",
    val cellulare : String? = ""

)
