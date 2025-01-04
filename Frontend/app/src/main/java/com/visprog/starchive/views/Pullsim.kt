

package com.visprog.starchive.views

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.visprog.starchive.R
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.UserModel
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.viewmodels.PullsimHistoryViewModel
import com.visprog.starchive.viewmodels.PullsimViewModel
import com.visprog.starchive.viewmodels.PullsimViewModelFactory
import java.time.LocalDate

data class PullResult(val item: BannerItemModel, val rarity: Int)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Pullsim(bannerModel: BannerModel, navController: NavController, pullsimHistoryViewModel: PullsimHistoryViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val userModel = UserModel(
        userId = 1,
        username = "testuser",
        password = "password",
        budgets = listOf(),
        plans = listOf(),
        expenses = listOf(),
        userGames = listOf(),
        hardPities = listOf(),
        token = "token"
    )

    val viewModel: PullsimViewModel = viewModel(factory = PullsimViewModelFactory(pullsimHistoryViewModel))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            Modifier.size(64.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xAA000000))
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = bannerModel.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = bannerModel.type,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.simulatePull(bannerModel, userModel) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCA311))
                        ) {
                            Text(
                                text = "1x Pull",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.simulateMultiplePulls(bannerModel, userModel, 10) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text(
                                text = "10x Pull",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_outline_description),
                        contentDescription = "Image",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable {
                            navController.navigate("pullsimhistory")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mdi_history),
                        contentDescription = "History",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val results by viewModel.results.collectAsState()
        results.forEach { result ->
            Text(
                text = "Pulled ${result.item.itemName} (Rarity ${result.rarity})",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=731dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
fun PullsimPreview() {
    val bannerModel = BannerModel(
        id = 1,
        gameId = 1,
        name = "Example Banner",
        type = "Gacha",
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(30),
        softPity = 75,
        items = listOf(
            BannerItemModel(1, 1, "5", "The Long Voyage Home", 0.6f),
            BannerItemModel(2, 1, "4", "Uncommon Item 1", 5.1f),
            BannerItemModel(3, 1, "3", "Common Item 1", 94.3f)
        )
    )
    val pullsimHistoryViewModel: PullsimHistoryViewModel = viewModel()
    StarchiveTheme(dynamicColor = false) {
        Pullsim(bannerModel, NavController(LocalContext.current), pullsimHistoryViewModel)
    }
}