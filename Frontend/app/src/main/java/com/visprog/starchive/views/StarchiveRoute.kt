

package com.visprog.starchive.views

/*import com.visprog.starchive.views.GameSelectionScreen*/
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
import com.visprog.starchive.viewmodels.BudgetingViewModel
import com.visprog.starchive.viewmodels.AuthViewModel
import com.visprog.starchive.viewmodels.GameSelectionViewModel
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.viewmodels.PullsimViewModel

@Composable
fun StarchiveApp(
    navController: NavHostController = rememberNavController(),
    homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
    pullsimViewModel: PullsimViewModel = viewModel(factory = PullsimViewModel.Factory),
    gameSelectionViewModel: GameSelectionViewModel = viewModel(factory = GameSelectionViewModel.Factory),
    budgetingViewModel: BudgetingViewModel = viewModel(factory = BudgetingViewModel.Factory)
) {
    val localContext = LocalContext.current
    val token = homepageViewModel.token.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (token.value != "Unknown" && token.value != "") {
            PagesEnum.GameChoice.name
        } else {
            PagesEnum.Login.name
        }
    ) {
        composable(route = PagesEnum.Login.name) {
            LoginView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authViewModel,
                navController = navController,
                context = localContext
            )
        }
        composable(route = PagesEnum.GameChoice.name) {
            GameSelectionScreen(
                navController = navController,
                gameSelectionViewModel = gameSelectionViewModel
            )
        }
        composable(route = "${PagesEnum.Home.name}/{gameId}", arguments = listOf(navArgument("gameId") { type = NavType.IntType })) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            HomepageView(
                homepageViewModel = homepageViewModel,
                token = token.value,
                gameId = gameId,
                navController = navController,
                context = localContext
            )
        }
        composable(route = "${PagesEnum.Budgeting.name}/{gameId}", arguments = listOf(navArgument("gameId") { type = NavType.IntType })) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            BudgetingView(
                budgetingViewModel = budgetingViewModel,
                token = token.value,
                gameId = gameId,
                navController = navController
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
        composable(route = "${PagesEnum.Pullsim.name}/{gameId}", arguments = listOf(navArgument("gameId") { type = NavType.IntType })) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            PullsimView(
                pullsimViewModel = pullsimViewModel,
                token = token.value,
                gameId = gameId,
                navController = navController
            )
        }
    }
}