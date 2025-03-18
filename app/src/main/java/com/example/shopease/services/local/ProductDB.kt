package com.example.shopease.services.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shopease.model.AddToCart
import com.example.shopease.model.Address
import com.example.shopease.model.ProductByCategoryItem
import com.example.shopease.services.local.converters.Converter

@Database(entities =[ProductByCategoryItem::class, AddToCart::class, Address::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ProductDB: RoomDatabase() {

    abstract fun getProductDao(): WishListDao

    abstract fun getCartDao(): AddToCartDao

    abstract fun getAddressDao(): AddressDao

}