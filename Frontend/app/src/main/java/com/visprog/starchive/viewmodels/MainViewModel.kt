/*



package com.visprog.starchive.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.UserModel
import java.time.LocalDate

class MainViewModel : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val dummyUser = UserModel(
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
                BannerItemModel(1, 1, "6", "Fugue 6", 0.6f),
                BannerItemModel(1, 1, "6", "Gepard 6", 0.6f),
                BannerItemModel(1, 1, "6", "Bronya 6", 0.6f),
                BannerItemModel(2, 1, "5", "Uncommon Item 1 5", 5.1f),
                BannerItemModel(3, 1, "4", "Common Item 1 4", 94.3f)
            )
        ),
        BannerModel(
            id = 2,
            gameId = 1,
            name = "Symphony",
            type = "The Grand Finale",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30),
            softPity = 75,
            items = listOf(
                BannerItemModel(4, 2, "6", "Symphony 6", 0.6f),
                BannerItemModel(5, 2, "6", "March 6", 0.6f),
                BannerItemModel(6, 2, "6", "Seele 6", 0.6f),
                BannerItemModel(7, 2, "5", "Uncommon Item 2 5", 5.1f),
                BannerItemModel(8, 2, "4", "Common Item 2 4", 94.3f)
            )
        )
    )

    val hardPityModels = listOf(
        HardPityModel(
            pityId = 1,
            userId = 1,
            gameId = 1,
            bannerId = 1,
            pityThreshold = 80,
            pullsTowardsPity = 0
        ),
        HardPityModel(
            pityId = 2,
            userId = 1,
            gameId = 1,
            bannerId = 2,
            pityThreshold = 80,
            pullsTowardsPity = 0
        )
    )
}*/


package com.visprog.starchive.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.GameModel
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.UserGameModel
import com.visprog.starchive.models.UserModel
import java.time.LocalDate

class MainViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val dummyBanners = listOf(
        BannerModel(
            id = 1,
            gameId = 1,
            name = "Fugue",
            type = "The Long Voyage Home",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30),
            softPity = 75,
            items = listOf(
                BannerItemModel(1, 1, "6", "Fugue 6", 0.6f),
                BannerItemModel(1, 1, "6", "Gepard 6", 0.6f),
                BannerItemModel(1, 1, "6", "Bronya 6", 0.6f),
                BannerItemModel(2, 1, "5", "Uncommon Item 1 5", 5.1f),
                BannerItemModel(3, 1, "4", "Common Item 1 4", 94.3f)
            )
        ),
        BannerModel(
            id = 2,
            gameId = 1,
            name = "Symphony",
            type = "The Grand Finale",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30),
            softPity = 75,
            items = listOf(
                BannerItemModel(4, 2, "6", "Symphony 6", 0.6f),
                BannerItemModel(5, 2, "6", "March 6", 0.6f),
                BannerItemModel(6, 2, "6", "Seele 6", 0.6f),
                BannerItemModel(7, 2, "5", "Uncommon Item 2 5", 5.1f),
                BannerItemModel(8, 2, "4", "Common Item 2 4", 94.3f)
            )
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val dummyGame = GameModel(
        id = 1,
        name = "Example Game",
        income = 1000000L,
        description = "This is a dummy game for testing purposes.",
        currency = "USD",
        userModels = listOf()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val dummyUser = UserModel(
        userId = 1,
        username = "testuser",
        password = "password",
        budgets = listOf(),
        plans = listOf(),
        expenses = listOf(),
        userGames = listOf(
            UserGameModel(
            userId = 1,
            gameId = dummyGame.id
        )
        ),
        hardPities = listOf(),
        token = "token"
    )

    val hardPityModels = listOf(
        HardPityModel(
            pityId = 1,
            userId = 1,
            gameId = 1,
            bannerId = 1,
            pityThreshold = 80,
            pullsTowardsPity = 0
        ),
        HardPityModel(
            pityId = 2,
            userId = 1,
            gameId = 1,
            bannerId = 2,
            pityThreshold = 80,
            pullsTowardsPity = 0
        )
    )
}