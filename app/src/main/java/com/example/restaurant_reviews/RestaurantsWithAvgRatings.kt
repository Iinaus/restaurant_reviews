package com.example.restaurant_reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.restaurant_reviews.models.RestaurantWithAvgRatingDto
import com.example.restaurant_reviews.models.RestaurantsWithAvgRatingState
import com.example.restaurant_reviews.ui.theme.Restaurant_reviewsTheme
import com.example.restaurant_reviews.vm.RestaurantsWithReviewsViewModel

@Composable
fun RestaurantsWithAvgRatingsRoot(
    modifier: Modifier = Modifier,
    viewModel: RestaurantsWithReviewsViewModel,
    onNavigate: () -> Unit) {

    val state by viewModel.restaurantState.collectAsStateWithLifecycle()
    RestaurantsWithAvgRatingsScreen(state = state, onNavigate = {chosenRestaurantId ->
        viewModel.setRestaurantId(chosenRestaurantId)
        onNavigate()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsWithAvgRatingsScreen(
    modifier: Modifier = Modifier,
    state: RestaurantsWithAvgRatingState,
    onNavigate: (Int) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Restaurants")
        }, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = "Open menu")            }
        })
    })  { paddingValues ->
        when {
            state.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.testTag("loader"))
                }
            }
            else -> {
                state.error?.let { err ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(err)
                    }
                } ?: LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)) {
                    items(state.restaurantsWithRatings, key = { restaurant ->
                        restaurant.id
                    }) { restaurantWithAvgRating ->
                        RestaurantWithAvgRatingItem(item = restaurantWithAvgRating, onNavigate = onNavigate)
                    }
                }
            }
        }

    }
}

@Composable
fun RestaurantWithAvgRatingItem(
    modifier: Modifier = Modifier,
    item: RestaurantWithAvgRatingDto,
    onNavigate: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigate(item.id)
            }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            AsyncImage(
                model = R.drawable.review,
                contentDescription = "Restaurant list placeholder image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(8.dp)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RatingBar(rating = item.rating ?: 0f, reviewCount = item.reviewCount)
                InfoBar(cuisine = item.cuisine, priceRange = item.priceRange)
                Text(
                    item.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.openStatus,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            }
        }
    }
}

@Composable
fun InfoBar(modifier: Modifier = Modifier, cuisine: String, priceRange: String) {
    Row {
        Spacer(modifier = Modifier.width(4.dp))
        Text(cuisine.toString(), style = MaterialTheme.typography.bodySmall,)
        Spacer(modifier = Modifier.width(4.dp))
        Text(priceRange.toString(), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@Preview
@Composable
private fun RestaurantsWithAvgRatingsScreenPreview(modifier: Modifier = Modifier) {
    Restaurant_reviewsTheme {
        val state =
            RestaurantsWithAvgRatingState(restaurantsWithRatings = listOf(
                RestaurantWithAvgRatingDto(
                    id = 1,
                    name = "Testiravintola",
                    cuisine = "Italian",
                    priceRange = "$$",
                    address = "Osoite 1, 05200, Nurmijärvi",
                    openStatus = "Open",
                    rating = 4.5f, reviewCount = 5),
                RestaurantWithAvgRatingDto(
                    id = 2,
                    name = "Toinen ravintola",
                    cuisine = "Greek",
                    priceRange = "$",
                    address = "Osoite 1, 05200, Paikkakunta",
                    openStatus = "Closing soon",
                    rating = 3f, reviewCount = 6)
            ))
        RestaurantsWithAvgRatingsScreen(state = state, onNavigate = {})
    }
}
