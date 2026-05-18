package com.example.kaushalyakarnataka.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaushalyakarnataka.ui.theme.BackgroundOffWhite
import com.example.kaushalyakarnataka.ui.theme.PrimaryOrange
import com.example.kaushalyakarnataka.ui.theme.TextPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Parallel animations
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        alpha.animateTo(1f, animationSpec = tween(1000))
        
        delay(1500) // Show splash for 2.5 seconds total
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOffWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value).alpha(alpha.value)
        ) {
            // Minimal Logo Placeholder (Stylized icon in a circle)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(PrimaryOrange.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // You can replace this Icon with Image(painterResource(R.drawable.karnataka_logo), ...)
                Icon(
                    imageVector = Icons.Default.Home, // Placeholder for minimal logo
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = PrimaryOrange
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "KAUSHALYA",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = 4.sp
            )
            Text(
                text = "KARNATAKA",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                color = PrimaryOrange,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Skill • Empower • Build",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
