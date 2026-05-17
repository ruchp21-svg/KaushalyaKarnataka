package com.example.kaushalyakarnataka.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaushalyakarnataka.R
import com.example.kaushalyakarnataka.data.Review
import com.example.kaushalyakarnataka.data.Service
import com.example.kaushalyakarnataka.data.Worker
import com.example.kaushalyakarnataka.ui.theme.KaushalyaKarnatakaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    worker: Worker,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${worker.phone}"))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.hire_me), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                ProfileHeader(worker, onBackClick)
            }
            item {
                StatsSection(worker)
            }
            item {
                SectionTitle(stringResource(R.string.services_offered))
            }
            items(worker.services) { service ->
                ServiceItem(service.serviceName, service.price)
            }
            item {
                SectionTitle(stringResource(R.string.review_wall))
            }
            items(worker.reviews) { review ->
                ReviewItem(review.authorName, review.starRating, review.commentText)
            }
        }
    }
}

@Composable
fun ProfileHeader(worker: Worker, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = worker.initials,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = worker.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.professional_service), color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun StatsSection(worker: Worker) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(worker.rating.toString(), stringResource(R.string.rating))
        StatItem(worker.reviewsCount.toString(), stringResource(R.string.reviews))
        StatItem(worker.jobsDone.toString(), stringResource(R.string.jobs_completed))
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun ServiceItem(name: String, price: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, fontWeight = FontWeight.Medium)
            Text(text = price, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ReviewItem(author: String, rating: Int, comment: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = author, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            repeat(rating) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB400), modifier = Modifier.size(16.dp))
            }
        }
        Text(text = comment, color = Color.DarkGray)
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    KaushalyaKarnatakaTheme {
        ProfileScreen(
            worker = Worker(
                id = "1",
                name = "Ramesh Kumar",
                role = "Plumber",
                location = "Bengaluru",
                initials = "RK",
                phone = "9876543210",
                isVerified = true,
                rating = 4.8,
                reviewsCount = 120,
                jobsDone = 150,
                services = listOf(
                    Service("1", "Pipe Leakage Repair", "₹500"),
                    Service("2", "Bathroom Fittings", "₹1200")
                ),
                reviews = listOf(
                    Review("1", "Suresh", "12 May 2026", 5, "Excellent work!")
                )
            ),
            onBackClick = {}
        )
    }
}
