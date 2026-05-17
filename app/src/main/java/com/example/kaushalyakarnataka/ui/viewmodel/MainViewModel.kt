package com.example.kaushalyakarnataka.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kaushalyakarnataka.data.Review
import com.example.kaushalyakarnataka.data.Service
import com.example.kaushalyakarnataka.data.Worker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _workers = MutableStateFlow<List<Worker>>(emptyList())
    val workers: StateFlow<List<Worker>> = _workers.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    init {
        _workers.value = listOf(
            Worker(
                id = "1", name = "Ramesh Kumar", role = "Plumber", location = "Bengaluru",
                rating = 4.8, reviewsCount = 120, jobsDone = 150, initials = "RK",
                phone = "9876543210", isVerified = true,
                services = listOf(Service("1", "Pipe Repair", "₹500")),
                reviews = listOf(Review("1", "Suresh", "12 May", 5, "Great!"))
            ),
            Worker(
                id = "2", name = "Savitha Devi", role = "Tailor", location = "Mysuru",
                rating = 4.9, reviewsCount = 85, jobsDone = 200, initials = "SD",
                phone = "9876543211", isVerified = true
            )
        )
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "en") "kn" else "en"
    }
}
