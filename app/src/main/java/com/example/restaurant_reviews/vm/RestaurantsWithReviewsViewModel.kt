package com.example.restaurant_reviews.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.restaurant_reviews.DataApi
import com.example.restaurant_reviews.models.RatingDto
import com.example.restaurant_reviews.models.RatingState
import com.example.restaurant_reviews.models.RestaurantDto
import com.example.restaurant_reviews.models.RestaurantsWithAvgRatingState
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsWithReviewsViewModel @Inject constructor(
    private val restaurantService: DataApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _restaurantState = MutableStateFlow(RestaurantsWithAvgRatingState())
    val restaurantState = _restaurantState.asStateFlow()

    private val _ratingsByRestaurantState = MutableStateFlow(RatingState())
    val ratingsByRestaurantState = _ratingsByRestaurantState.asStateFlow()

    init {
        getRestaurantsWithReviews()
    }

    fun setRestaurantId(id: Int) {
        savedStateHandle["restaurantId"] = id
    }

    fun getRestaurantsWithReviews() {
        viewModelScope.launch {
            try {
                _restaurantState.update { currentState ->
                    currentState.copy(loading = true, error = null)
                }

                val restaurantReviews = restaurantService.getRestaurantsWithReviews()

                _restaurantState.update { currentState ->
                    currentState.copy(restaurantsWithRatings = restaurantReviews)
                }

            } catch (e: Exception) {
                _restaurantState.update { currentState ->
                    currentState.copy(error = e.toString())
                }
            } finally {
                _restaurantState.update { currentState ->
                    currentState.copy(loading = false)
                }
            }
        }
    }

    fun deleteRating(ratingId: Int) {
        viewModelScope.launch {

            _ratingsByRestaurantState.update { currentState ->
                currentState.copy(loading = true)
            }

            try {

                savedStateHandle.get<Int>("restaurantId")?.let { rId ->
                    restaurantService.removeRating(rId, ratingId)
                } ?: throw Exception("restaurant id cannot be found")

                val reviews = _ratingsByRestaurantState.value.restaurant?.reviews ?: emptyList()
                val remainingReviews = reviews.filter { rating ->
                    rating?.id != ratingId
                }

                val restaurant = _ratingsByRestaurantState.value.restaurant
                val newRestaurant = RestaurantDto(
                    id = restaurant?.id ?: 0,
                    name = restaurant?.name ?: "",
                    cuisine = restaurant?.cuisine ?: "",
                    priceRange = restaurant?.priceRange ?: "",
                    address = restaurant?.address ?: "",
                    openStatus = restaurant?.openStatus ?: "",
                    rating = restaurant?.rating ?: 0f,
                    reviewCount = restaurant?.reviewCount ?: 0,
                    reviews = remainingReviews)

                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(restaurant = newRestaurant)
                }

            } catch (e: Exception) {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(error = e.toString())
                }
            } finally {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(loading = false)
                }
            }
        }
    }

    fun getRestaurant() {
        viewModelScope.launch {
            try {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(loading = true, error = null)
                }

                savedStateHandle.get<Int>("restaurantId")?.let { rId ->
                    val restaurant = restaurantService.getRestaurant(rId)
                    _ratingsByRestaurantState.update { currentState ->
                        currentState.copy(restaurant = restaurant)
                    }
                }

            } catch (e: Exception) {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(error = e.toString())
                }
            } finally {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(loading = false)
                }
            }
        }
    }

    fun getRestaurantRating() {
        viewModelScope.launch {
            try {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(loading = true, error = null)
                }

                savedStateHandle.get<Int>("restaurantId")?.let { rId ->
                    val restaurant = restaurantService.getRestaurant(rId)
                    val ratings = restaurantService.getRestaurantRatings(rId)

                    val newRestaurant = restaurant?.copy(reviews = ratings)

                    _ratingsByRestaurantState.update { currentState ->
                        currentState.copy(restaurant = newRestaurant)
                    }
                }

            } catch (e: Exception) {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(error = e.toString())
                }
            } finally {
                _ratingsByRestaurantState.update { currentState ->
                    currentState.copy(loading = false)
                }
            }
        }
    }
}