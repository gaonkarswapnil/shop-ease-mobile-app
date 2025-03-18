package com.example.shopease.di

import android.content.Context
import androidx.room.Room
import com.example.shopease.services.local.AddToCartDao
import com.example.shopease.services.local.AddressDao
import com.example.shopease.services.local.ProductDB
import com.example.shopease.services.local.WishListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductDB{
        return Room.databaseBuilder(
            context.applicationContext,
            ProductDB::class.java,
            "productDB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: ProductDB): WishListDao{
        return db.getProductDao()
    }

    @Provides
    @Singleton
    fun provideAddCartDao(db: ProductDB): AddToCartDao{
        return db.getCartDao()
    }

    @Provides
    @Singleton
    fun provideAddressDao(db: ProductDB): AddressDao{
        return db.getAddressDao()
    }
}