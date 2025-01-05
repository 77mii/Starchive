


package com.visprog.starchive.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.visprog.starchive.viewmodels.PullsimHistoryViewModel



import androidx.compose.ui.graphics.Color

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PullsimHistory(navController: NavController, viewModel: PullsimHistoryViewModel) {
    val pullLogs by viewModel.pullLogs.collectAsState()

    val highestRarity = pullLogs.maxOfOrNull { it.rarity } ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6E0D0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pull History",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(pullLogs) { index, pullResult ->
                val textColor = when (pullResult.rarity) {
                    highestRarity -> Color(0xFFFFD700) // Gold for highest rarity
                    highestRarity - 1 -> Color(0xFF800080) // Purple for second highest rarity
                    else -> Color.Black // Default color for other rarities
                }
                Text(
                    text = "${index + 1}. Pulled ${pullResult.item.itemName} (Rarity ${pullResult.rarity})",
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}