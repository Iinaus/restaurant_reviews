package com.example.restaurant_reviews

import androidx.lifecycle.SavedStateHandle
import com.example.restaurant_reviews.models.RatingDto
import com.example.restaurant_reviews.models.RestaurantDto
import com.example.restaurant_reviews.models.RestaurantWithAvgRatingDto
import com.example.restaurant_reviews.vm.RestaurantsWithReviewsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MockProductServiceOK: DataApi {
    override suspend fun getRestaurantsWithReviews(): List<RestaurantWithAvgRatingDto> {
        return listOf(
            RestaurantWithAvgRatingDto(
                id = 1,
                name = "Testiravintola",
                cuisine = "Italian",
                priceRange = "$$",
                address = "Osoite 1, 05200, Nurmijärvi",
                openStatus = "Open",
                rating = 4.5f, reviewCount = 5)
        )
    }

    override suspend fun getRestaurant(restaurantId: Int): RestaurantDto? {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantRatings(restaurantId: Int): List<RatingDto?> {
        TODO("Not yet implemented")
    }

    override suspend fun removeRating(restaurantId: Int, ratingId: Int) {
        TODO("Not yet implemented")
    }
}

class MockProductServiceNOK: DataApi {
    override suspend fun getRestaurantsWithReviews(): List<RestaurantWithAvgRatingDto> {
        throw Exception("Error")
    }

    override suspend fun getRestaurant(restaurantId: Int): RestaurantDto? {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantRatings(restaurantId: Int): List<RatingDto?> {
        TODO("Not yet implemented")
    }

    override suspend fun removeRating(restaurantId: Int, ratingId: Int) {
        TODO("Not yet implemented")
    }
}

class RestaurantsWithReviewsViewModelTest {
    private lateinit var vm: RestaurantsWithReviewsViewModel
    private lateinit var vmNOK: RestaurantsWithReviewsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val restaurantService = MockProductServiceOK()
        val restaurantServiceNOK = MockProductServiceNOK()

        val savedStateHandle = SavedStateHandle()
        vm = RestaurantsWithReviewsViewModel(restaurantService, savedStateHandle)
        vmNOK = RestaurantsWithReviewsViewModel(restaurantServiceNOK, savedStateHandle)
    }

    @Test
    fun getRestaurantsWithAvgRatingsOK() {
        val expectedData = listOf(
            RestaurantWithAvgRatingDto(
                id = 1,
                name = "Testiravintola",
                cuisine = "Italian",
                priceRange = "$$",
                address = "Osoite 1, 05200, Nurmijärvi",
                openStatus = "Open",
                rating = 4.5f, reviewCount = 5)
        )

        assertEquals(expectedData, vm.restaurantState.value.restaurantsWithRatings)
    }

    @Test
    fun getRestaurantsWithAvgRatingsNOK() {
        vmNOK.getRestaurantRating()
        assertNotNull(vmNOK.restaurantState.value.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}