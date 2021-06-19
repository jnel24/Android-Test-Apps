package com.nelson.testapp.data

import androidx.lifecycle.LiveData

/**
 * Repository class for storing and accessing [OfferItem].
 */
class OfferRepository(var database: AppDatabase) {

    private var offers: LiveData<List<OfferItem>>? = null

    fun loadOffers(): LiveData<List<OfferItem>>? {
        offers = database.offerDao()?.getOffers()
        return offers
    }

    fun getOffer(id: String) : LiveData<OfferItem>? {
        return database.offerDao()?.getOffer(id)
    }

    suspend fun insertOffers(offers: List<OfferItem>) {
        database.offerDao()?.insertAll(offers)
    }

    suspend fun insertOffer(offer: OfferItem) {
        database.offerDao()?.insert(offer)
    }
}