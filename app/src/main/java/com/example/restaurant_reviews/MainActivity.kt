package com.example.restaurant_reviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.restaurant_reviews.ui.theme.Restaurant_reviewsTheme
import com.example.restaurant_reviews.vm.RestaurantsWithReviewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Restaurant_reviewsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "RestaurantsWithReviewFeature") {
                    navigation("RestaurantsWithAvgRatings", route = "RestaurantsWithReviewFeature") {
                        composable("RestaurantsWithAvgRatings") { navGraph ->
                            val viewModel = navGraph.SharedViewModel<RestaurantsWithReviewsViewModel>(navController)
                            RestaurantsWithAvgRatingsRoot(viewModel = viewModel, onNavigate = { chosenRestaurantId ->
                                viewModel.setRestaurantId(chosenRestaurantId)
                                navController.navigate("Ratings")
                            })
                        }
                        composable("Ratings") { navGraph ->
                            val viewModel = navGraph.SharedViewModel<RestaurantsWithReviewsViewModel>(navController)
                            RatingsScreenRoot(viewModel = viewModel, onNavigate = {
                                navController.navigateUp()
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.SharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)

}