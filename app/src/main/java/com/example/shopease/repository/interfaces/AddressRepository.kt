package com.example.shopease.repository.interfaces

import com.example.shopease.model.Address

interface AddressRepository {

    suspend fun insertAddress(address: Address)

    suspend fun getAllAddress(): List<Address>

    suspend fun removeAddress(id: Int)
}