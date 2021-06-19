package com.nelson.testapp.viewmodel

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.data.OfferRepository
import com.nelson.testapp.viewmodel.OffersViewModel.Action
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel for storing the latest offers
 */
class OffersViewModel(private val offerRepo: OfferRepository) : BaseViewModel<Action>() {

    private val offers = offerRepo.loadOffers()

    override fun handleAction(action: Action) {
        when (action) {
            is Action.LoadOffers -> readJsonDataFromAsset(action.assetManager)
        }
    }

    fun getOffer(id: String): LiveData<OfferItem>? {
        return offerRepo.getOffer(id)
    }

    fun getOffers(): LiveData<List<OfferItem>>? {
        return offers
    }

    suspend fun setOffers(offers: List<OfferItem>) {
        offerRepo.insertOffers(offers)
    }

    suspend fun addOffer(offer: OfferItem) {
        offerRepo.insertOffer(offer)
    }

    /**
     * Retrieves JSON data as String from asset .json file
     *
     * @return String representing JSON data
     */
    private fun readJsonDataFromAsset(assetManager: AssetManager) {
        viewModelScope.launch {
            var jsonString: String? = null
            try {
                jsonString = assetManager.open("offers.json").bufferedReader().use {
                    it.readText()
                }
            } catch (ioException: IOException) {
                Log.e("Local JSON error", "error in JSON file $ioException")
            }
            jsonString?.let {
                loadOffersIntoRepository(jsonString)
            }
        }
    }

    /**
     * Converts JSON data from String to List<OfferItem> and loads into OfferRepository
     *
     * @param json - String representing JSON data for parsing
     */
    private fun loadOffersIntoRepository(json: String) {
        viewModelScope.launch {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val type = Types.newParameterizedType(List::class.java, OfferItem::class.java)
            val offerAdapter = moshi.adapter<List<OfferItem>>(type)
            offerAdapter.fromJson(json)?.let { setOffers(it) }
        }
    }

    sealed class Action {
        data class LoadOffers(val assetManager: AssetManager) : Action()
    }
}