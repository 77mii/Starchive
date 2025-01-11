

package com.visprog.starchive.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.uiStates.PullsimDataStatusUIState
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.viewmodels.PullsimViewModel
import com.visprog.starchive.views.templates.BannerCardTemplate
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun PullsimView(
    pullsimViewModel: PullsimViewModel = viewModel(factory = HomepageViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController
) {
    val context = LocalContext.current
    val dataStatus by pullsimViewModel.dataStatus.collectAsState()

    LaunchedEffect(token) {
        pullsimViewModel.getBannersbyGameId(token, gameId)
    }

    LaunchedEffect(dataStatus) {
        when (dataStatus) {
            is PullsimDataStatusUIState.Failed -> {
                val errorMessage = (dataStatus as PullsimDataStatusUIState.Failed).errorMessage
                Toast.makeText(context, "DATA ERROR: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.d("PullsimView", "DATA ERROR: $errorMessage")
                pullsimViewModel.clearDataErrorMessage()
            }
            else -> {}
        }
    }

    CommonTemplate(currentScreen = "Pullsim", onNavigate = { screen ->
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
                    LazyColumn(
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        items(pullsim) { banner ->
                            BannerCardTemplate(
                                firstItem = (banner.items?.firstOrNull()?.bannerId.toString() ?: "No items"),
                                bannername = banner.bannerName.toString(),
                                bannerimage = banner.imageUrl ?: "",
                                onCardClick = {
                                    // Handle card click
                                },
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                            )
                        }
                    }
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
                is PullsimDataStatusUIState.Start -> {
                    Text(
                        text = "Start loading pullsim data",
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