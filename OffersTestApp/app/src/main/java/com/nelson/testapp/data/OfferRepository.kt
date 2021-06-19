package com.nelson.testapp.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for storing and accessing [OfferItem].
 */
@Singleton
class OfferRepository @Inject constructor(private val offerDao: OfferDao) {

    fun loadOffers(): LiveData<List<OfferItem>> {
        return offerDao.getOffers()
    }

    fun getOffer(id: String) : LiveData<OfferItem>? {
        return offerDao.getOffer(id)
    }

    suspend fun insertOffers(offers: List<OfferItem>) {
        offerDao.insertAll(offers)
    }

    suspend fun insertOffer(offer: OfferItem) {
        offerDao.insert(offer)
    }
}