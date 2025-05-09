package com.example.restaurant_reviews

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    reviewCount: Int,
    showReviewCount: Boolean = true) {
    val filledStars = rating.toInt()
    val hasHalfStar = rating - filledStars >= 0.5
    val emptyStars = 5 - filledStars - if (hasHalfStar) 1 else 0

    Row {
        repeat(filledStars) {
            Icon(Icons.Filled.Star, contentDescription = "Full star", tint = Color(0xFFFFD700))
        }

        if(hasHalfStar) {
            Icon(
                painterResource(id = R.drawable.star_half) ,
                contentDescription = "Full star",
                tint = Color(0xFFFFD700)
            )
        }

        repeat(emptyStars) {
            Icon(Icons.Filled.Star, contentDescription = "Empty star", tint = Color.LightGray)
        }

        Spacer(modifier = Modifier.width(4.dp))
        Text(rating.toString())
        Spacer(modifier = Modifier.width(4.dp))
        if (showReviewCount) {
            Text("($reviewCount)", color = Color.Gray)
        }

    }

}