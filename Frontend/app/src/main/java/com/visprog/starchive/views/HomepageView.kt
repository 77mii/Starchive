package com.visprog.starchive.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.visprog.starchive.R
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.uiStates.ArticleDataStatusUIState
import com.visprog.starchive.uiStates.BannerDataStatusUIState
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import com.visprog.starchive.uiStates.GameDataStatusUIState
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun HomepageView(
    homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController,
    context: Context
) {
    val budgetDataStatus by homepageViewModel.budgetDataStatus.collectAsStateWithLifecycle()
    val articleDataStatus by homepageViewModel.articleDataStatus.collectAsStateWithLifecycle()
    val bannerDataStatus by homepageViewModel.bannerDataStatus.collectAsStateWithLifecycle()
    val gameDataStatus by homepageViewModel.gameDataStatus.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homepageViewModel.getBudgets(token, gameId)
        homepageViewModel.getArticles(token, gameId)
        homepageViewModel.getBanners(token, gameId)
        homepageViewModel.getGame(token, gameId)
    }

    CommonTemplate(
        currentScreen = "Homepage",
        gameId = gameId,
        onNavigate = { screen, gameId ->
            when (screen) {
                "Homepage" -> navController.navigate("${PagesEnum.Homepage.name}/$gameId")
                "Budgeting" -> navController.navigate("${PagesEnum.Budgeting.name}/$gameId")
                "Pullsim" -> navController.navigate("${PagesEnum.Pullsim.name}/$gameId")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (budgetDataStatus) {
                is BudgetDataStatusUIState.Success -> {
                    val budget = (budgetDataStatus as BudgetDataStatusUIState.Success).data
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                            .requiredHeight(161.dp)
                            .border(
                                width = 4.dp,
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Column(
                            modifier =
                            Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.secondary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .fillMaxWidth()
                                .requiredHeight(161.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            when (gameDataStatus) {
                                is GameDataStatusUIState.Success -> {
                                    val game =
                                        (gameDataStatus as GameDataStatusUIState.Success).data
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 20.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "REAL CASH",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Rp ${budget.data.remaining_budget.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Column (
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Button(
                                                onClick = { homepageViewModel.logoutUser(token, navController) },
                                                modifier = Modifier.requiredSize(45.dp).clip(CircleShape),
                                                colors = ButtonDefaults.buttonColors(Color.Red),

                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_logout),
                                                    contentDescription = null,
                                                    colorFilter = ColorFilter.tint(Color.White),
                                                    modifier = Modifier.requiredSize(20.dp)
                                                )
                                            }
                                        }

                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(horizontalAlignment = Alignment.Start) {
                                            Text(
                                                text = game.data.currencyName?.uppercase()
                                                    ?: "Currency",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "${budget.data.remaining_currency.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }

                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = game.data.ticketsName?.uppercase()
                                                    ?: "Tickets",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "${budget.data.remaining_tickets.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }

                                is GameDataStatusUIState.Failed -> {
                                    Text(
                                        text = "Failed to load game data",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }

                                else -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                            }

                        }
                    }
                    Box(
                        modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(y = (-176).dp, x = (-64).dp)
                            .zIndex(1f)
                            .background(
                                color = Color(0xFFFFA500),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AVAILABLE BUDGET",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                is BudgetDataStatusUIState.Failed -> {
                    Text(
                        text = "Failed to load budget data",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp)) {
                Row {
                    when (articleDataStatus) {
                        is ArticleDataStatusUIState.Success -> {
                            val articles =
                                (articleDataStatus as ArticleDataStatusUIState.Success)
                                    .data
                                    .data
                            LazyColumn(modifier = Modifier.padding(end = 8.dp)) {
                                items(articles) { article ->
                                    Box(
                                        modifier =
                                        Modifier
                                            .padding(bottom = 20.dp)
                                            .requiredSize(
                                                width = 140.dp,
                                                height = 161.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                MaterialTheme.colorScheme
                                                    .secondary
                                            )
                                            .clickable { /* TODO: handle click to article */
                                            },
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            verticalArrangement = Arrangement.Bottom
                                        ) {
                                            Text(
                                                text = article.title,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is ArticleDataStatusUIState.Failed -> {
                            Text(text = "Failed to load article data", color = Color.Red)
                        }

                        else -> {
                            CircularProgressIndicator()
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    when (bannerDataStatus) {
                        is BannerDataStatusUIState.Success -> {
                            val banners = (bannerDataStatus as BannerDataStatusUIState.Success).data
                            LazyColumn(modifier = Modifier.padding(start = 8.dp)) {
                                itemsIndexed(banners) { index, banner ->
                                    Box(
                                        modifier =
                                        Modifier
                                            .padding(bottom = 20.dp)
                                            .requiredSize(
                                                width = 220.dp,
                                                height =
                                                if (index == 0) 342.dp
                                                else 161.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                MaterialTheme.colorScheme
                                                    .secondary
                                            )
                                            .clickable { /* TODO: handle click to banner */
                                            },
                                    ) {
                                        AsyncImage(
                                            model =
                                            ImageRequest.Builder(LocalContext.current)
                                                .data(banner.imageUrl)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "Banner Image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center
                                        )

                                        Box(
                                            modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Color.Black.copy(
                                                        alpha = 0.4f
                                                    )
                                                )
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            verticalArrangement = Arrangement.Bottom
                                        ) {
                                            Text(
                                                text = banner.type,
                                                fontFamily =
                                                MaterialTheme.typography
                                                    .bodyLarge
                                                    .fontFamily,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = banner.bannerName.toString(),
                                                fontFamily =
                                                MaterialTheme.typography
                                                    .titleLarge
                                                    .fontFamily,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is BannerDataStatusUIState.Failed -> {
                            Text(text = "Failed to load banner data", color = Color.Red)
                        }

                        else -> {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun HomepagePreview() {
    StarchiveTheme(dynamicColor = false) {
        HomepageView(
            token = "sample_token",
            gameId = 1,
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }
}
