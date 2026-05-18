package com.example.kaushalyakarnataka.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaushalyakarnataka.ui.home.HomeScreen
import com.example.kaushalyakarnataka.ui.profile.ProfileScreen
import com.example.kaushalyakarnataka.ui.registration.RegistrationScreen
import com.example.kaushalyakarnataka.ui.settings.SettingsScreen
import com.example.kaushalyakarnataka.ui.viewmodel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    val workers by viewModel.workers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val favoriteWorkerIds by viewModel.favoriteWorkerIds.collectAsState()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                workers = workers.filter { it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true) },
                searchQuery = searchQuery,
                favoriteWorkerIds = favoriteWorkerIds,
                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                onWorkerClick = { worker -> navController.navigate("profile/${worker.id}") },
                onToggleLanguage = { viewModel.toggleLanguage() },
                onRegisterClick = { navController.navigate("registration") },
                onSettingsClick = { navController.navigate("settings") },
                onToggleFavorite = { viewModel.toggleFavorite(it) }
            )
        }
        composable("profile/{workerId}") { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("workerId")
            val worker = workers.find { it.id == workerId }
            worker?.let {
                ProfileScreen(
                    worker = it,
                    isFavorite = favoriteWorkerIds.contains(it.id),
                    onBackClick = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavorite(it.id) }
                )
            }
        }
        composable("registration") {
            RegistrationScreen(onBackClick = { navController.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
