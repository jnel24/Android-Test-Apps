package com.nelson.testapp.di

import android.content.Context
import com.nelson.testapp.data.AppDatabase
import com.nelson.testapp.data.OfferDao
import com.nelson.testapp.data.OfferRepository
import com.nelson.testapp.viewmodel.OffersViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideOfferDao(appDatabase: AppDatabase): OfferDao {
        return appDatabase.offerDao()
    }
}