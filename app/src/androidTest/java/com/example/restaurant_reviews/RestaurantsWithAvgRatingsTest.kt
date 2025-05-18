package com.example.restaurant_reviews

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.restaurant_reviews.models.RestaurantsWithAvgRatingState
import com.example.restaurant_reviews.ui.theme.Restaurant_reviewsTheme
import org.junit.Rule
import org.junit.Test


class ProductsWithAvgRatingsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testCircularProgressIndicatorVisible() {
        composeRule.setContent {
            Restaurant_reviewsTheme {
                val state = RestaurantsWithAvgRatingState(loading = true)
                RestaurantsWithAvgRatingsScreen(state = state, onNavigate = {})
            }
        }

        composeRule.onNodeWithTag("loader").assertExists()
    }
}