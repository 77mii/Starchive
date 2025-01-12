package com.visprog.starchive.views

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.viewmodels.PullsimBannerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PullsimBannerView(
    bannerId: Int,
    navController: NavHostController
) {
    val pullsimBannerViewModel: PullsimBannerViewModel = viewModel(
        factory = PullsimBannerViewModel.Factory
    )
    val bannerItems by pullsimBannerViewModel.bannerItems.collectAsState()
    val banner by pullsimBannerViewModel.banner.collectAsState()
    val pity by pullsimBannerViewModel.pity.collectAsState()
    val gachaResults by pullsimBannerViewModel.gachaResults.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(bannerId) {
        Log.d("PullSimBannerView", "LaunchedEffect triggered for bannerId: $bannerId")
        pullsimBannerViewModel.getBannerItems(bannerId)
        pullsimBannerViewModel.getBanner(bannerId)
        pullsimBannerViewModel.getPity(bannerId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Pullsim Banner View for Banner ID: $bannerId")
        LazyColumn {
            items(bannerItems) { (bannerItem, item) ->
                Text(text = "Item Name: ${item?.itemName ?: "Unknown"}, Acquire Rate: ${bannerItem.acquireRate}")
            }
        }
        
        banner?.let { bannerModel ->
            pity?.let { pityModel ->
                val remainingPulls = (bannerModel.hardPity ?: 0) - (pityModel.pullTowardsPity)
                Text(text = "Pulls remaining towards pity: $remainingPulls")
            }
        }
        Button(onClick = { 
            pullsimBannerViewModel.performGachaPull(bannerId)
            showDialog = true
            coroutineScope.launch {
                delay(3000)
                showDialog = false
            }
        }) {
            Text(text = "Perform Gacha Pull")
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Gacha Results") },
                text = {
                    LazyColumn {
                        itemsIndexed(gachaResults) { index, item ->
                            Text(text = "${index + 1}. Item Name: ${item.itemName}, Rarity: ${item.rarity}")
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}