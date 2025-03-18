package com.example.shopease.di

import com.example.shopease.repository.implementation.AddressRepositoryImpl
import com.example.shopease.repository.implementation.CartRepositoryImpl
import com.example.shopease.repository.implementation.CategoriesRepositoryImpl
import com.example.shopease.repository.implementation.LoginRepositoryImpl
import com.example.shopease.repository.implementation.ProductDetailRepositoryImpl
import com.example.shopease.repository.implementation.UserRepositoryImpl
import com.example.shopease.repository.implementation.WishlistRepositoryImpl
import com.example.shopease.repository.interfaces.AddressRepository
import com.example.shopease.repository.interfaces.CartRepository
import com.example.shopease.repository.interfaces.CategoriesRepository
import com.example.shopease.repository.interfaces.LoginRepository
import com.example.shopease.repository.interfaces.ProductDetailRepository
import com.example.shopease.repository.interfaces.UserRepository
import com.example.shopease.repository.interfaces.WishlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    @Singleton
    fun provideUserModule(user: UserRepositoryImpl): UserRepository{
        return user
    }

    @Provides
    @Singleton
    fun provideLoginModule(user: LoginRepositoryImpl): LoginRepository{
        return user
    }

    @Provides
    @Singleton
    fun provideCategoryModule(category: CategoriesRepositoryImpl): CategoriesRepository {
        return category
    }

    @Provides
    @Singleton
    fun provideWishList(wishlist: WishlistRepositoryImpl): WishlistRepository{
        return wishlist
    }

    @Provides
    @Singleton
    fun provideProduct(product: ProductDetailRepositoryImpl): ProductDetailRepository{
        return product
    }

    @Provides
    @Singleton
    fun provideCart(cart: CartRepositoryImpl): CartRepository{
        return cart
    }

    @Provides
    @Singleton
    fun provideAddress(address: AddressRepositoryImpl): AddressRepository{
        return address
    }
}