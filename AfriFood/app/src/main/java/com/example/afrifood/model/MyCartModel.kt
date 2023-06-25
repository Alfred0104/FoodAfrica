package com.example.afrifood.model

class MyCartModel {

    // variables
    var productId : String?=null
    var coverImg : String?=null
    var name : String?=null
    var price : String?=null
    var productType : String ?=null
    var timestamp : Long = 0
    var quantity = 0
    var totalPrice = 0f


    //empty costructor (required by firebase)
    constructor()

    //parameterized constructor
    /*constructor(
        productId: String,
        coverImg: String,
        name: String,
        price : String,
        productType : String,
        timestamp: Long,
        quantity : Long,
        totalPrice : Float
    ){
        this.productId = productId
        this.coverImg = coverImg
        this.name = name
        this.price = price
        this.productType = productType
        this.timestamp = timestamp
        this.quantity = quantity
        this.totalPrice = totalPrice

    }*/



}