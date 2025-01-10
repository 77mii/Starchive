/*
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
import com.visprog.starchive.viewmodels.HomepageViewModel

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
}*//*



*/
/*package com.visprog.starchive.routes*//*

package com.visprog.starchive.views
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
*/
/*import com.visprog.starchive.views.GameSelectionScreen*//*

import com.visprog.starchive.views.HomepageView
import com.visprog.starchive.views.LoginView
*/
/*import com.visprog.starchive.views.PullSimSelection
import com.visprog.starchive.views.Pullsim
import com.visprog.starchive.views.PullsimDetails
import com.visprog.starchive.views.PullsimHistory*//*

import com.visprog.starchive.viewmodels.HomepageViewModel

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()

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
                composable("homepage") {
                    mainViewModel.dummyUser.token?.let { it1 ->
                        HomepageView(
                            homepageViewModel = viewModel(),
                            token = it1,
                            gameId = 1
                        )
                    }
                }
            }
        }
    }
}*/






package com.visprog.starchive.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.viewmodels.AuthViewModel
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.views.HomepageView
/*import com.visprog.starchive.views.GameSelectionScreen*/
import com.visprog.starchive.views.LoginView
import com.visprog.starchive.views.RegisterView


@Composable
fun StarchiveApp(
    navController: NavHostController = rememberNavController(),
    homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),



    ){
    val localContext = LocalContext.current
    val token = homepageViewModel.token.collectAsState()

    NavHost(navController = navController, startDestination = if(token.value !="Unknown"&& token.value != ""){
PagesEnum.Home.name
    }else{
PagesEnum.Login.name
    }){

composable(route = PagesEnum.Login.name){
LoginView(
    modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
    authenticationViewModel = authViewModel,
    navController = navController,
    context = localContext
)
}

        composable(route = PagesEnum.Home.name) {
            HomepageView(
                homepageViewModel = homepageViewModel,
                token = token.value,
                gameId = 1
            )
        }

        composable(route = PagesEnum.Signup.name) {
            RegisterView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authViewModel,
                navController = navController,
                context = localContext
            )
        }

    }
}
