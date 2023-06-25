package com.example.afrifood.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StoreProductDao {

    @Insert
    suspend fun insertProduct(product : StoreProductModel)

    @Delete
    suspend fun deleteProduct(product : StoreProductModel)

    @Query("SELECT * FROM product")
    fun getAllProducts() : LiveData<List<StoreProductModel>>

    @Query("SELECT * FROM product WHERE productId = :id")
    fun isExit(id : String) : StoreProductModel
}