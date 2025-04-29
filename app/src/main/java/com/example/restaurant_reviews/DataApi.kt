package com.example.restaurant_reviews

import com.example.restaurant_reviews.models.RestaurantWithAvgRatingDto
import retrofit2.http.GET

interface DataApi {
    @GET("restaurants/ratings")
    suspend fun getRestaurantsWithReviews(): List<RestaurantWithAvgRatingDto>
}