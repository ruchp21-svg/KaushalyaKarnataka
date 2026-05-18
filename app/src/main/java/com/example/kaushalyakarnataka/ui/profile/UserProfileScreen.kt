package com.example.kaushalyakarnataka.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kaushalyakarnataka.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("Ruchitha P") }
    var userEmail by remember { mutableStateOf("ruchp21@example.com") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
        if (uri != null) {
            Toast.makeText(context, "Profile picture updated locally!", Toast.LENGTH_SHORT).show()
        }
    }

    if (isEditing) {
        EditProfileDialog(
            currentName = userName,
            currentEmail = userEmail,
            onDismiss = { isEditing = false },
            onSave = { newName, newEmail ->
                userName = newName
                userEmail = newEmail
                isEditing = false
                Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceWhite),
                modifier = Modifier.drawBehindBorder()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackgroundOffWhite)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card with Image Upload
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = SurfaceWhite,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(AccentOrangeLight)
                            .clickable { photoPickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (profileImageUri != null) {
                            AsyncImage(
                                model = profileImageUri,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                tint = PrimaryOrange
                            )
                        }
                        
                        // Overlaid Camera Icon
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .background(PrimaryOrange, CircleShape)
                                .border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.CameraAlt,
                                contentDescription = "Change Picture",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Text(text = userName, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = userEmail, fontSize = 14.sp, color = TextSecondary)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Account Settings",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                modifier = Modifier.align(Alignment.Start).padding(start = 8.dp, bottom = 12.dp)
            )
            
            ProfileOptionItem(
                icon = Icons.Default.Edit, 
                title = "Edit Personal Info",
                onClick = { isEditing = true }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProfileOptionItem(
                icon = Icons.Default.Star, 
                title = "My Favorites",
                onClick = { Toast.makeText(context, "Favorites clicked", Toast.LENGTH_SHORT).show() }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProfileOptionItem(
                icon = Icons.Default.Settings, 
                title = "App Preferences",
                onClick = { Toast.makeText(context, "Preferences clicked", Toast.LENGTH_SHORT).show() }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { onBackClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEE2E2)),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("Logout", color = Color(0xFF991B1B), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun EditProfileDialog(
    currentName: String,
    currentEmail: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var email by remember { mutableStateOf(currentEmail) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        focusedLabelColor = PrimaryOrange,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        focusedLabelColor = PrimaryOrange,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, email) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ProfileOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    title: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceWhite,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, 
                contentDescription = null, 
                tint = PrimaryOrange,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title, 
                fontWeight = FontWeight.SemiBold, 
                color = TextPrimary,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight, 
                contentDescription = null, 
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private fun Modifier.drawBehindBorder() = this.background(BorderLight)
