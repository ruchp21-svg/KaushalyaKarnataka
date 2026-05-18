package com.example.kaushalyakarnataka.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaushalyakarnataka.R
import com.example.kaushalyakarnataka.data.Worker
import com.example.kaushalyakarnataka.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    workers: List<Worker>,
    searchQuery: String,
    favoriteWorkerIds: Set<String>,
    onSearchQueryChange: (String) -> Unit,
    onWorkerClick: (Worker) -> Unit,
    onToggleLanguage: () -> Unit,
    onRegisterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onToggleFavorite: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Plumber", "Electrician", "Tailor", "Cook")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.SemiBold, fontSize = 18.sp) },
                actions = {
                    IconButton(onClick = onToggleLanguage) {
                        Icon(Icons.Default.Language, contentDescription = "Language", tint = TextPrimary)
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceWhite,
                    titleContentColor = TextPrimary
                ),
                modifier = Modifier.drawBehindBorder()
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onRegisterClick,
                containerColor = PrimaryOrange,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.start_business), fontWeight = FontWeight.Medium) },
                shape = RoundedCornerShape(24.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BackgroundOffWhite)
        ) {
            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(14.dp)),
                placeholder = { Text("Search electricians, plumbers...", color = Color(0xFF9CA3AF), fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            // Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        onClick = { selectedCategory = category },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) PrimaryOrange else Color(0xFFF5F5F5),
                        contentColor = if (isSelected) Color.White else TextSecondary
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Worker List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredWorkers = workers.filter {
                    (it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true)) &&
                    (selectedCategory == "All" || it.role == selectedCategory)
                }
                items(filteredWorkers) { worker ->
                    WorkerCard(
                        worker = worker,
                        isFavorite = favoriteWorkerIds.contains(worker.id),
                        onWorkerClick = onWorkerClick,
                        onToggleFavorite = { onToggleFavorite(worker.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun WorkerCard(
    worker: Worker,
    isFavorite: Boolean,
    onWorkerClick: (Worker) -> Unit,
    onToggleFavorite: () -> Unit
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onWorkerClick(worker) }
            .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = SurfaceWhite
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AccentOrangeLight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = worker.initials,
                    color = PrimaryOrange,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = worker.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TextPrimary)
                    if (worker.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = SuccessGreen,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                
                // Role Badge
                Surface(
                    color = AccentOrangeLight,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = worker.role,
                        color = PrimaryOrange,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = "📍 ${worker.location}", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Star, contentDescription = null, tint = StarYellow, modifier = Modifier.size(12.dp))
                    Text(text = " ${worker.rating}", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onToggleFavorite, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${worker.phone}"))
                    context.startActivity(intent)
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Quick Call",
                        tint = PrimaryOrange,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

fun Modifier.drawBehindBorder() = this.padding(bottom = 1.dp).background(BorderLight).padding(bottom = (-1).dp)
