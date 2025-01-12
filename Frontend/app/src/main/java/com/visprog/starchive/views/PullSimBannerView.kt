

// package com.visprog.starchive.views

// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.lazy.LazyColumn
// import androidx.compose.foundation.lazy.items
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
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

//     pullsimBannerViewModel.getBannerItems(bannerId)

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
//     }
// }

package com.visprog.starchive.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    pullsimBannerViewModel.getBannerItems(bannerId)

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
    }
}