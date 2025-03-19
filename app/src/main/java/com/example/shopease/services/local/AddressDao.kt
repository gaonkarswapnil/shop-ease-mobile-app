package com.example.shopease.services.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shopease.model.Address

@Dao
interface AddressDao {

    @Insert
    suspend fun addAddress(address: Address)

    @Query("select * from address_table")
    suspend fun getAddress(): List<Address>

    @Query("delete from address_table where id= :id")
    suspend fun removeAddress(id: Int)

    @Query("select * from address_table where id= :id")
    suspend fun getAddressFromId(id: Int): Address
}