package com.example.afrifood.model

import com.google.protobuf.Timestamp

class FavModel { // We will use this model to show the user favorites

    // variables
    var productId : String = ""
    var coverImg : String = ""
    var name : String = ""
    var productType : String = ""
    var timestamp : Long = 0
    var isFavorite = false

    //empty costructor (required by firebase)
    constructor()

    //parameterized constructor
    constructor(
        productId: String,
        coverImg: String,
        name: String,
        productType : String,
        timestamp: Long,
        isFavorite : Boolean
    ){
        this.productId = productId
        this.coverImg = coverImg
        this.name = name
        this.productType = productType
        this.timestamp = timestamp
        this.isFavorite = isFavorite

    }

}