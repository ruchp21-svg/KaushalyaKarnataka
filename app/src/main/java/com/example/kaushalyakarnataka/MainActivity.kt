package com.example.kaushalyakarnataka

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.kaushalyakarnataka.ui.navigation.NavGraph
import com.example.kaushalyakarnataka.ui.theme.KaushalyaKarnatakaTheme
import com.example.kaushalyakarnataka.ui.viewmodel.MainViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val language by viewModel.currentLanguage.collectAsState()
            
            KaushalyaKarnatakaTheme {
                val locale = Locale(language)
                val configuration = Configuration(LocalConfiguration.current)
                configuration.setLocale(locale)
                val context = LocalContext.current.createConfigurationContext(configuration)

                CompositionLocalProvider(LocalContext provides context) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
