package com.visprog.starchive.viewmodels



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.Banner
import com.visprog.starchive.models.BannerItem
import java.time.LocalDate

class MainViewModel : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val banners = listOf(
        Banner(
            id = 1,
            gameId = 1,
            name = "Fugue",
            type = "The Long Voyage Home",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30),
            softPity = 75,
            items = listOf(
                BannerItem(1, 1, "5", "Fugue", 0.6f),
                BannerItem(1, 1, "5", "Gepard", 0.6f),
                BannerItem(1, 1, "5", "Bronya", 0.6f),
                BannerItem(2, 1, "4", "Uncommon Item 1", 5.1f),
                BannerItem(3, 1, "3", "Common Item 1", 94.3f)
            )
        )
    )
}