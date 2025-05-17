package com.example.restaurant_reviews

import androidx.lifecycle.SavedStateHandle
import com.example.restaurant_reviews.vm.RestaurantsWithReviewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class RestaurantsWithReviewsViewModelTest {
    private lateinit var vm: RestaurantsWithReviewsViewModel
    private lateinit var vmNOK: RestaurantsWithReviewsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val savedStateHandle = SavedStateHandle()
    }

    @Test
    fun getRestaurantsWithAvgRatingsOK() {

    }

    @Test
    fun getRestaurantsWithAvgRatingsNOK() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}