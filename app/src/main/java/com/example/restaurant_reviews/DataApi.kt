package com.example.restaurant_reviews

import com.example.restaurant_reviews.models.RatingDto
import com.example.restaurant_reviews.models.RestaurantDto
import com.example.restaurant_reviews.models.RestaurantWithAvgRatingDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DataApi {
    @GET("restaurants/ratings")
    suspend fun getRestaurantsWithReviews(): List<RestaurantWithAvgRatingDto>

    @GET("restaurants/{restaurantId}")
    suspend fun getRestaurant(@Path("restaurantId") restaurantId: Int): RestaurantDto?

    @GET("restaurants/{restaurantId}/ratings")
    suspend fun getRestaurantRatings(@Path("restaurantId") restaurantId: Int): List<RatingDto?>

    @DELETE("restaurants/{restaurantId}/ratings/{ratingId}")
    suspend fun removeRating(@Path("restaurantId") restaurantId: Int, @Path("ratingId") ratingId: Int)
}