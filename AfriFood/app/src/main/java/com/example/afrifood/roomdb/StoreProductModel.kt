package com.example.afrifood.roomdb

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class StoreProductModel(
    @PrimaryKey
    @NonNull
    val productId : String,
    @ColumnInfo(name = "productName")
    val productName : String? = "",
    @ColumnInfo(name = "productImage")
    val productImage : String? = "",
    @ColumnInfo(name = "productSp")
    val productSp : String? = "",

)
