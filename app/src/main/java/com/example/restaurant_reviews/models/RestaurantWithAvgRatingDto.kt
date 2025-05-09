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

data class RestaurantDto(
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
    val reviewCount: Int,
    val reviews: List<RatingDto?> = emptyList()
)

data class RatingDto(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int?,
    val value: Float,
    val description: String?,
    @SerializedName("date_rated")
    val dateRated: String
)

data class RatingState(
    val error: String? = null,
    val loading: Boolean = false,
    val restaurant: RestaurantDto? = null
)
