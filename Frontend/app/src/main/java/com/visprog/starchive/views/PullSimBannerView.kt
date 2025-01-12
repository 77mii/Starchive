

// package com.visprog.starchive.views

// import android.util.Log
// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.lazy.items
// import androidx.compose.material3.Button
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.LaunchedEffect
// import androidx.compose.runtime.collectAsState
// import androidx.compose.runtime.getValue
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.unit.dp
// import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.navigation.NavHostController
// import com.visprog.starchive.viewmodels.PullsimBannerViewModel

// @Composable
// fun PullsimBannerView(
//     bannerId: Int,
//     navController: NavHostController
// ) {
//     val pullsimBannerViewModel: PullsimBannerViewModel = viewModel(
//         factory = PullsimBannerViewModel.Factory
//     )
//     val bannerItems by pullsimBannerViewModel.bannerItems.collectAsState()
//     val pity by pullsimBannerViewModel.pity.collectAsState()

//     LaunchedEffect(bannerId) {
//         Log.d("PullSimBannerView", "LaunchedEffect triggered for bannerId: $bannerId")
//         pullsimBannerViewModel.getBannerItems(bannerId)
//         pullsimBannerViewModel.getPity(bannerId)
//     }

//     Column(
//         modifier = Modifier
//             .fillMaxSize()
//             .padding(16.dp)
//     ) {
//         Text(text = "Pullsim Banner View for Banner ID: $bannerId")
//         LazyColumn {
//             items(bannerItems) { (bannerItem, item) ->
//                 Text(text = "Item Name: ${item?.itemName ?: "Unknown"}, Acquire Rate: ${bannerItem.acquireRate}")
//             }
//         }
//         pity?.let {
//             Text(text = "Pity: ${it.pullTowardsPity}")
//             Button(onClick = { /* Implement update or delete functionality here */ }) {
//                 Text(text = "Update Pity")
//             }
//             Button(onClick = { /* Implement delete functionality here */ }) {
//                 Text(text = "Delete Pity")
//             }
//         }
//     }
// }

package com.visprog.starchive.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.viewmodels.PullsimBannerViewModel

@Composable
fun PullsimBannerView(
    bannerId: Int,
    navController: NavHostController
) {
    val pullsimBannerViewModel: PullsimBannerViewModel = viewModel(
        factory = PullsimBannerViewModel.Factory
    )
    val bannerItems by pullsimBannerViewModel.bannerItems.collectAsState()
    val pity by pullsimBannerViewModel.pity.collectAsState()
    val gachaResults by pullsimBannerViewModel.gachaResults.collectAsState()

    LaunchedEffect(bannerId) {
        Log.d("PullSimBannerView", "LaunchedEffect triggered for bannerId: $bannerId")
        pullsimBannerViewModel.getBannerItems(bannerId)
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
        pity?.let {
            Text(text = "Pity: ${it.pullTowardsPity}")
        }
        Button(onClick = { pullsimBannerViewModel.performGachaPull(bannerId) }) {
            Text(text = "Perform Gacha Pull")
        }
        Text(text = "Gacha Results:")
        LazyColumn {
            items(gachaResults) { item ->
                Text(text = "Item Name: ${item.itemName}, Rarity: ${item.rarity}")
            }
        }
    }
}