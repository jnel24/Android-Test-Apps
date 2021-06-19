package com.nelson.testapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the [OfferItem] class.
 */
@Dao
interface OfferDao {

    @Query("SELECT * FROM offers ORDER BY name")
    fun getOffers(): LiveData<List<OfferItem>>

    @Query("SELECT * FROM offers WHERE id = :id")
    fun getOffer(id: String): LiveData<OfferItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(offer: OfferItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(offers: List<OfferItem>)

}