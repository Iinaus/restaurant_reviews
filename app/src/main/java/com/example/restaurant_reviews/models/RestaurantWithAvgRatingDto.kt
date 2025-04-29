package com.example.restaurant_reviews.models

import com.google.gson.annotations.SerializedName

data class RestaurantWithAvgRatingDto(
    val id: Int,
    val name: String,
    val cuisine: String,
    @SerializedName("price_range")
    val priceRange: String,
    val address: String,
    @SerializedName("open_status")
    val openStatus: String,
    val rating: Float?,
    @SerializedName("review_count")
    val reviewCount: Int
)

data class RestaurantsWithAvgRatingState(
    val error: String? = null,
    val loading: Boolean = false,
    val restaurantsWithRatings: List<RestaurantWithAvgRatingDto> = emptyList()
)
