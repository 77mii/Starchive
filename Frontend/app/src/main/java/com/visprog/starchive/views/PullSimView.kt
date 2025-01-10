package com.visprog.starchive.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.uiStates.PullsimDataStatusUIState
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.viewmodels.PullsimViewModel
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun PullsimView(
    pullsimViewModel: PullsimViewModel = viewModel(factory = HomepageViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController
){
    val dataStatus by pullsimViewModel.dataStatus.collectAsStateWithLifecycle()

    LaunchedEffect(pullsimViewModel.dataStatus) {
        val dataStatus = pullsimViewModel.dataStatus.value

    }

    CommonTemplate(currentScreen="Pullsim", onNavigate = { screen ->
        when (screen) {
            "Home" -> navController.navigate(PagesEnum.Home.name)
            "Budgeting" -> navController.navigate(PagesEnum.Budgeting.name)
            "Pullsim" -> navController.navigate(PagesEnum.Pullsim.name)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            when (dataStatus) {
                is PullsimDataStatusUIState.Success -> {
                    val pullsim = (dataStatus as PullsimDataStatusUIState.Success).data
                    Text(
                        text = "Pullsim",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is PullsimDataStatusUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                    )
                }
                is PullsimDataStatusUIState.Failed -> {
                    Text(
                        text = "Failed to load pullsim data",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    Text(
                        text = "No data available",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
        }
    }
}