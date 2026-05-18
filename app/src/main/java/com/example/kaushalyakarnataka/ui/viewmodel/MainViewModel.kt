package com.example.kaushalyakarnataka.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaushalyakarnataka.data.Worker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    private val _workers = MutableStateFlow<List<Worker>>(emptyList())
    val workers: StateFlow<List<Worker>> = _workers.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _favoriteWorkerIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteWorkerIds: StateFlow<Set<String>> = _favoriteWorkerIds.asStateFlow()

    init {
        fetchWorkers()
    }

    private fun fetchWorkers() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("workers").get().await()
                val workerList = snapshot.toObjects(Worker::class.java)
                if (workerList.isEmpty()) {
                    // Fallback to dummy data if Firestore is empty
                    _workers.value = getDummyWorkers()
                } else {
                    _workers.value = workerList
                }
            } catch (e: Exception) {
                _workers.value = getDummyWorkers()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "en") "kn" else "en"
    }

    fun toggleFavorite(workerId: String) {
        val currentFavorites = _favoriteWorkerIds.value
        _favoriteWorkerIds.value = if (currentFavorites.contains(workerId)) {
            currentFavorites - workerId
        } else {
            currentFavorites + workerId
        }
    }

    private fun getDummyWorkers() = listOf(
        Worker(
            id = "1", name = "Ramesh Kumar", role = "Plumber", location = "Bengaluru",
            rating = 4.8, reviewsCount = 120, jobsDone = 150, initials = "RK",
            phone = "9876543210", isVerified = true
        ),
        Worker(
            id = "2", name = "Savitha Devi", role = "Tailor", location = "Mysuru",
            rating = 4.9, reviewsCount = 85, jobsDone = 200, initials = "SD",
            phone = "9876543211", isVerified = true
        )
    )
}
