/*


package com.visprog.starchive.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.visprog.starchive.viewmodels.MainViewModel
import com.visprog.starchive.viewmodels.PullsimHistoryViewModel
import com.visprog.starchive.views.PullSimSelection
import com.visprog.starchive.views.Pullsim
import com.visprog.starchive.views.PullsimDetails
import com.visprog.starchive.views.PullsimHistory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val pullsimHistoryViewModel: PullsimHistoryViewModel = viewModel()
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
            )
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "pullsimselection") {
                composable("pullsimselection") {
                    PullSimSelection(navController)
                }
                composable(
                    "pullsim/{bannerId}",
                    arguments = listOf(navArgument("bannerId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val bannerId = backStackEntry.arguments?.getInt("bannerId")
                    val banner = mainViewModel.dummyBanners.find { it.id == bannerId }
                    if (banner != null) {
                        Pullsim(banner, navController, pullsimHistoryViewModel, mainViewModel)
                    }
                }
                composable(
                    "pullsimdetails/{bannerId}",
                    arguments = listOf(navArgument("bannerId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val bannerId = backStackEntry.arguments?.getInt("bannerId")
                    val banner = mainViewModel.dummyBanners.find { it.id == bannerId }
                    if (banner != null) {
                        PullsimDetails(banner, navController)
                    }
                }
                composable("pullsimhistory") {
                    PullsimHistory(navController, pullsimHistoryViewModel)
                }
            }
        }
    }
}
*/


package com.visprog.starchive.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.visprog.starchive.viewmodels.MainViewModel
import com.visprog.starchive.viewmodels.PullsimHistoryViewModel
import com.visprog.starchive.views.GameSelectionScreen
import com.visprog.starchive.views.LoginView
import com.visprog.starchive.views.PullSimSelection
import com.visprog.starchive.views.Pullsim
import com.visprog.starchive.views.PullsimDetails
import com.visprog.starchive.views.PullsimHistory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val pullsimHistoryViewModel: PullsimHistoryViewModel = viewModel()
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
            )
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "login") {
                composable("login") {
                    LoginView(mainViewModel, navController)
                }
                composable("gameSelection") {
                    GameSelectionScreen(navController, mainViewModel)
                }
                composable(
                    "pullsimselection/{gameId}",
                    arguments = listOf(navArgument("gameId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val gameId = backStackEntry.arguments?.getInt("gameId")
                    val banners = mainViewModel.dummyBanners.filter { it.gameId == gameId }
                    PullSimSelection(navController, banners)
                }
                composable(
                    "pullsim/{bannerId}",
                    arguments = listOf(navArgument("bannerId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val bannerId = backStackEntry.arguments?.getInt("bannerId")
                    val banner = mainViewModel.dummyBanners.find { it.id == bannerId }
                    if (banner != null) {
                        Pullsim(banner, navController, pullsimHistoryViewModel, mainViewModel)
                    }
                }
                composable(
                    "pullsimdetails/{bannerId}",
                    arguments = listOf(navArgument("bannerId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val bannerId = backStackEntry.arguments?.getInt("bannerId")
                    val banner = mainViewModel.dummyBanners.find { it.id == bannerId }
                    if (banner != null) {
                        PullsimDetails(banner, navController)
                    }
                }
                composable("pullsimhistory") {
                    PullsimHistory(navController, pullsimHistoryViewModel)
                }
            }
        }
    }
}