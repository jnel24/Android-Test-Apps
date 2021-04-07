package com.nelson.testapp.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * A model representing an Order.
 *
 * @property id - The identifier of the order
 * @property url - The web link to the product's image for the order
 * @property name - The name of the product for the order
 * @property description - The details of the order
 * @property value - The current "cash back" value of the order
 * @property isFavorite - Whether the order is a favorite of the user or not
 */
class OfferItem(val id: String,
                val url: String?,
                val name: String,
                val description: String,
                @Json(name = "current_value") val value: String,
                var isFavorite: Boolean = false) : Serializable