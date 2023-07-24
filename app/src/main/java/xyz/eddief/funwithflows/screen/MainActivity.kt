package xyz.eddief.funwithflows.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.eddief.funwithflows.DisplaySection
import xyz.eddief.funwithflows.ui.theme.FunWithFlowsTheme

// https://kotlinlang.org/docs/flow.html

class MainActivity : ComponentActivity() {

    private val displaySections = DisplaySection.sections

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunWithFlowsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "entry",
                    ) {
                        composable(route = "entry") {
                            EntryScreen(displaySections) {
                                navController.navigate(it.route)
                            }
                        }
                        displaySections.map { s ->
                            composable(s.route) {
                                DisplayScreen(viewModel = viewModel(s.vm), title = s.route)
                            }
                        }
                    }
                }
            }
        }
    }
}