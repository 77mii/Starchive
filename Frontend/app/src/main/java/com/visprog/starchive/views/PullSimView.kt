/*
package com.visprog.starchive.views
import android.widget.Toast
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.visprog.starchive.views.templates.BannerCardTemplate
import com.visprog.starchive.views.templates.CommonTemplate
import android.content.Context
import androidx.compose.ui.platform.LocalContext
@Composable
fun PullsimView(
    pullsimViewModel: PullsimViewModel = viewModel(factory = HomepageViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController,
    context: Context
){
    val dataStatus = pullsimViewModel.dataStatus

    */
/*LaunchedEffect(pullsimViewModel.dataStatus) {

       *//*
*/
/* pullsimViewModel.getBannersbyGameId(gameId)*//*
*/
/*

    }*//*

    LaunchedEffect(token){
        pullsimViewModel.getBannersbyGameId(gameId)
    }
    */
/*LaunchedEffect(dataStatus){
    if(dataStatus is PullsimDataStatusUIState.Failed){
        Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
        pullsimViewModel.clearErrorMessage()
    }*//*


    LaunchedEffect(dataStatus) {
        if (dataStatus is PullsimDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            pullsimViewModel.clearDataErrorMessage()
        }
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
                   */
/*LazyColumn(
                       modifier = Modifier
                           .padding(vertical = 8.dp)
                           .clip(RoundedCornerShape(10.dp))
                   ) {


                   }*//*

                    LazyColumn(
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        items(pullsim) { banner ->
                            BannerCardTemplate(
                                firstItem = (banner.items.firstOrNull()?.bannerId ?: "No items").toString(),
                                bannername = banner.bannerName,
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
}*/


package com.visprog.starchive.views

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
    val dataStatus = pullsimViewModel.dataStatus

    LaunchedEffect(token) {
        pullsimViewModel.getBannersbyGameId(gameId)
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is PullsimDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            pullsimViewModel.clearDataErrorMessage()
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
                                firstItem = (banner.items.firstOrNull()?.bannerId ?: "No items").toString(),
                                bannername = banner.bannerName,
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