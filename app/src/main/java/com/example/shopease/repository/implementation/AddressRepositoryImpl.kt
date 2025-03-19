package com.example.shopease.repository.implementation

import com.example.shopease.model.Address
import com.example.shopease.repository.interfaces.AddressRepository
import com.example.shopease.services.local.AddressDao
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
): AddressRepository {

    override suspend fun insertAddress(address: Address) {
        addressDao.addAddress(address)
    }

    override suspend fun getAllAddress(): List<Address> {
        return addressDao.getAddress()
    }

    override suspend fun removeAddress(id: Int) {
        return addressDao.removeAddress(id)
    }

    override suspend fun getAddressFromId(id: Int): Address {
        return addressDao.getAddressFromId(id)
    }


}