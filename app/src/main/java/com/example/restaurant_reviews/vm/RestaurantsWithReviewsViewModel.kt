package com.example.restaurant_reviews.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurant_reviews.models.RestaurantsWithAvgRatingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsWithReviewsViewModel @Inject constructor() : ViewModel() {
    private val _restaurantState = MutableStateFlow(RestaurantsWithAvgRatingState())
    val restaurantState = _restaurantState.asStateFlow()

    init {
        getRestaurantsWithReviews()
    }

    private fun getRestaurantsWithReviews() {
        viewModelScope.launch {
            try {
                _restaurantState.update { currentState ->
                    currentState.copy(loading = true, error = null)
                }

                // rajapintahaku tähän

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
}