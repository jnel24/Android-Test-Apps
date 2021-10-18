package com.nelson.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nelson.testapp.data.OfferItem
import com.nelson.testapp.data.OfferRepository
import com.nelson.testapp.viewmodel.OffersViewModel.Action
import com.nelson.testapp.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for storing the latest offers
 */
@HiltViewModel
class OffersViewModel @Inject constructor(
    private val offerRepo: OfferRepository,
) : BaseViewModel<Action>() {

    override fun handleAction(action: Action) {
        when (action) {
            is Action.UpdateOffer -> addOffer(action.item)
        }
    }

    fun getOffer(id: String): LiveData<OfferItem>? {
        return offerRepo.getOffer(id)
    }

    fun getOffers(): LiveData<List<OfferItem>>? {
        return offerRepo.loadOffers()
    }

    private fun addOffer(offer: OfferItem) {
        viewModelScope.launch {
            offerRepo.insertOffer(offer)
        }
    }

    sealed class Action {
        data class UpdateOffer(val item: OfferItem) : Action()
    }
}