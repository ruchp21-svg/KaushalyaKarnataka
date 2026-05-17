package com.example.kaushalyakarnataka.ui.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaushalyakarnataka.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(onBackClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.register_expert)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        // Icon would go here
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.full_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = skill,
                onValueChange = { skill = it },
                label = { Text(stringResource(R.string.primary_skill)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = area,
                onValueChange = { area = it },
                label = { Text(stringResource(R.string.service_area)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(stringResource(R.string.phone_number)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Handle registration */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.register_expert), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
