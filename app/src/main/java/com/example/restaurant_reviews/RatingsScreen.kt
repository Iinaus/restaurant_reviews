package com.example.restaurant_reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.restaurant_reviews.models.RatingDto
import com.example.restaurant_reviews.models.RatingState
import com.example.restaurant_reviews.models.RestaurantDto
import com.example.restaurant_reviews.ui.theme.Restaurant_reviewsTheme
import com.example.restaurant_reviews.vm.RestaurantsWithReviewsViewModel

@Composable
fun RatingsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RestaurantsWithReviewsViewModel,
    onNavigate: () -> Unit) {

    val state by viewModel.ratingsByRestaurantState.collectAsStateWithLifecycle()
    RatingScreen(state = state, onNavigate = onNavigate)

    LaunchedEffect(true) {
        viewModel.getRestaurant()
        viewModel.getRestaurantRating()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen(modifier: Modifier = Modifier, state: RatingState, onNavigate: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(state.restaurant?.name ?: "Ratings")
        }, navigationIcon = {
            IconButton(onClick = { onNavigate() }) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
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
                    CircularProgressIndicator()
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
                } ?: LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    item {
                        state.restaurant?.let { restaurant ->
                            RestaurantCard(item = restaurant)
                        }
                    }
                    items(state.restaurant?.reviews ?: emptyList(), key = { restaurant ->
                        restaurant?.id ?: ""
                    }) { rating ->
                        if (rating != null) {
                            RatingItem(item = rating)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun RestaurantCard(modifier: Modifier = Modifier, item: RestaurantDto) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            AsyncImage(
                model = R.drawable.review,
                contentDescription = "Restaurant list placeholder image",
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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
fun RatingItem(modifier: Modifier = Modifier, item: RatingDto) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                RatingBar(rating = item.value ?: 0f, reviewCount = 1, showReviewCount = false)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Remove Rating")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            item.description?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                item.dateRated,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun RatingsScreenPreview(modifier: Modifier = Modifier) {
    Restaurant_reviewsTheme {
        val state =
            RatingState(restaurant =
                RestaurantDto(
                    id = 1,
                    name = "Ravintola",
                    cuisine = "Italian",
                    priceRange = "$$",
                    address = "Osoite",
                    openStatus = "Closing soon" ,
                    rating = 3f,
                    reviewCount = 4,
                    reviews = listOf(
                        RatingDto(
                        id = 1,
                        userId = null,
                        value = 4.5f,
                        description = "Lipsum",
                        dateRated = "Tähän joku päivämäärä ja kellonaika"),
                        RatingDto(
                            id = 2,
                            userId = null,
                            value = 0f,
                            description = null,
                            dateRated = "Tähän joku päivämäärä ja kellonaika")
                    )
                )
            )
        RatingScreen(state = state, onNavigate = { })
    }
}