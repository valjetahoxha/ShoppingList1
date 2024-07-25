package hu.ait.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint

import hu.ait.data.ShoppingListRepository
import hu.ait.navigation.MainNavigation
import hu.ait.screen.AddEditItemScreen
import hu.ait.screen.ShoppingListScreen
import hu.ait.screen.SplashScreen
import hu.ait.shoppinglist.ui.MyApp



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppNavHost()
        }
    }
}

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.SplashScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.SplashScreen.route) {
            SplashScreen(
                navController,
                onSplashCompleted = {
                    navController.navigate(MainNavigation.ShoppingListScreen.route)
                }
            )
        }
        composable(MainNavigation.ShoppingListScreen.route) {
            ShoppingListScreen()
        }
        composable(MainNavigation.AddEditItemScreen.route) {
            AddEditItemScreen()
        }
    }
}