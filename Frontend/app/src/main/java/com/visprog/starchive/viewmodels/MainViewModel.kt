package com.visprog.starchive.viewmodels



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import java.time.LocalDate

class MainViewModel : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val bannerModels = listOf(
        BannerModel(
            id = 1,
            gameId = 1,
            name = "Fugue",
            type = "The Long Voyage Home",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30),
            softPity = 75,
            items = listOf(
                BannerItemModel(1, 1, "5", "Fugue", 0.6f),
                BannerItemModel(1, 1, "5", "Gepard", 0.6f),
                BannerItemModel(1, 1, "5", "Bronya", 0.6f),
                BannerItemModel(2, 1, "4", "Uncommon Item 1", 5.1f),
                BannerItemModel(3, 1, "3", "Common Item 1", 94.3f)
            )
        )
    )
}