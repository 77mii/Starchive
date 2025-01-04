


package com.visprog.starchive

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.viewmodels.MainViewModel
import com.visprog.starchive.viewmodels.PullsimHistoryViewModel
import com.visprog.starchive.views.PullSimSelection
import com.visprog.starchive.views.Pullsim
import com.visprog.starchive.views.PullsimHistory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            StarchiveApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StarchiveApp() {
    StarchiveTheme {
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
                        val banner = mainViewModel.bannerModels.find { it.id == bannerId }
                        if (banner != null) {
                            Pullsim(banner, navController, pullsimHistoryViewModel)
                        }
                    }
                    composable("pullsimhistory") {
                        PullsimHistory(navController, pullsimHistoryViewModel)
                    }
                }
            }
        }
    }
}