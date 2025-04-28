package com.example.restaurant_reviews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.restaurant_reviews.ui.theme.Restaurant_reviewsTheme

@Composable
fun RestaurantsWithAvgRatingsRoot(modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsWithAvgRatingsScreen(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Restaurants")
        }, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = "Open menu")            }
        })
    })  { paddingValues -> paddingValues }
}

@Preview
@Composable
private fun RestaurantsWithAvgRatingsScreenPreview(modifier: Modifier = Modifier) {
    Restaurant_reviewsTheme {
        RestaurantsWithAvgRatingsScreen()
    }
}
