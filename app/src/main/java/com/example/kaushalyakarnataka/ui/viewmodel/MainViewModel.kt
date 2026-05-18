package com.example.kaushalyakarnataka.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaushalyakarnataka.data.Worker
import com.example.kaushalyakarnataka.data.Service
import com.example.kaushalyakarnataka.data.Review
// import com.google.firebase.firestore.FirebaseFirestore
// import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {
    /* 
    // Uncomment once google-services.json is added
    private val db = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }
    */

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
        _workers.value = getDummyWorkers()
        // fetchWorkers() // Re-enable once Firebase is configured
    }

    /*
    private fun fetchWorkers() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("workers").get().await()
                val workerList = snapshot.toObjects(Worker::class.java)
                if (workerList.isEmpty()) {
                    _workers.value = getDummyWorkers()
                } else {
                    _workers.value = workerList
                }
            } catch (e: Exception) {
                _workers.value = getDummyWorkers()
            }
        }
    }
    */

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
            phone = "9876543210", isVerified = true,
            services = listOf(Service("1", "Pipe Repair", "₹500"), Service("2", "Fitting", "₹800")),
            reviews = listOf(Review("1", "Suresh", "12 May", 5, "Great service!"), Review("2", "Amit", "10 May", 4, "Good work."))
        ),
        Worker(
            id = "2", name = "Savitha Devi", role = "Tailor", location = "Mysuru",
            rating = 4.9, reviewsCount = 85, jobsDone = 200, initials = "SD",
            phone = "9876543211", isVerified = true,
            services = listOf(Service("3", "Saree Blouse", "₹400"), Service("4", "Alteration", "₹100")),
            reviews = listOf(Review("3", "Priya", "15 May", 5, "Perfect fitting!"))
        ),
        Worker(
            id = "3", name = "Manjunath S.", role = "Electrician", location = "Hubballi",
            rating = 4.7, reviewsCount = 95, jobsDone = 120, initials = "MS",
            phone = "9876543212", isVerified = false,
            services = listOf(Service("5", "Wiring", "₹1500"), Service("6", "Fan Repair", "₹300")),
            reviews = listOf(Review("4", "Kiran", "1 May", 4, "Very punctual."))
        )
    )
}
