package xyz.eddief.funwithflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.eddief.funwithflows.ui.theme.FunWithFlowsTheme
import xyz.eddief.funwithflows.viewmodel.CombineViewModel
import xyz.eddief.funwithflows.viewmodel.MergeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunWithFlowsTheme {
                // A surface container using the 'background' color from the theme
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
                            EntryScreen {
                                navController.navigate(it.route)
                            }
                        }
                        composable(route = Section.Combine.route) {
                            DisplayScreen(
                                viewModel = viewModel<CombineViewModel>(),
                                title = Section.Combine.route
                            )
                        }
                        composable(route = Section.Merge.route) {
                            DisplayScreen(
                                viewModel = viewModel<MergeViewModel>(),
                                title = Section.Merge.route
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FunWithFlowsTheme {
        Greeting("Android")
    }
}