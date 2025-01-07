package com.visprog.starchive.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.uiStates.HomepageDataStatusUIState
import com.visprog.starchive.viewModels.HomepageViewModel

@Composable
fun StarchiveApp(
    navController: NavHostController = rememberNavController(),
    homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory),
) {

    val token = homepageViewModel.token.collectAsState()

    NavHost(navController = navController, startDestination = "homepage") {
        composable(route = "homepage") {
            HomepageView(
                homepageViewModel = homepageViewModel,
                token = token.value,
                gameId = 1
            )
        }
    }
}