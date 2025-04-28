package com.example.restaurant_reviews.models

data class RestaurantWithAvgRatingDto(
    val id: Int,
    val name: String,
    val cuisine: String,
    val priceRange: String,
    val address: String,
    val openStatus: String,
    val rating: Float?,
    val reviewCount: Int
)

data class RestaurantsWithAvgRatingState(
    val error: String? = null,
    val loading: Boolean = false,
    val productsWithRatings: List<RestaurantWithAvgRatingDto> = emptyList()
)
