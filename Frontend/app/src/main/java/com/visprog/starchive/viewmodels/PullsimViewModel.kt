/*


package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visprog.starchive.models.Banner
import com.visprog.starchive.models.BannerItem
import com.visprog.starchive.models.HardPity
import com.visprog.starchive.models.User
import com.visprog.starchive.views.PullResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class PullsimViewModel(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel
) : ViewModel() {
    private val _results = MutableStateFlow<List<PullResult>>(emptyList())
    val results: StateFlow<List<PullResult>> = _results

    private val _hardPity = MutableStateFlow(HardPity(1, 1, 1, 1, 0))
    val hardPity: StateFlow<HardPity> = _hardPity

    private val _firstRarity5Acquired = MutableStateFlow(false)
    val firstRarity5Acquired: StateFlow<Boolean> = _firstRarity5Acquired

    fun simulatePull(banner: Banner, user: User) {
        viewModelScope.launch {
            val (result, newFirstRarity5Acquired) = simulatePull(banner, user, _hardPity.value, _firstRarity5Acquired.value)
            _results.value = _results.value + result
            _firstRarity5Acquired.value = newFirstRarity5Acquired
            pullsimHistoryViewModel.addPullResult(result)
            if (result.rarity == 5) {
                _hardPity.value = _hardPity.value.copy(pullsTowardsPity = 0)
            }
        }
    }

    fun simulateMultiplePulls(banner: Banner, user: User, numberOfPulls: Int) {
        viewModelScope.launch {
            var newFirstRarity5Acquired = _firstRarity5Acquired.value
            repeat(numberOfPulls) {
                val (result, updatedFirstRarity5Acquired) = simulatePull(banner, user, _hardPity.value, newFirstRarity5Acquired)
                _results.value = _results.value + result
                newFirstRarity5Acquired = updatedFirstRarity5Acquired
                pullsimHistoryViewModel.addPullResult(result)
                if (result.rarity == 5) {
                    _hardPity.value = _hardPity.value.copy(pullsTowardsPity = 0)
                }
            }
            _firstRarity5Acquired.value = newFirstRarity5Acquired
        }
    }

    private fun simulatePull(banner: Banner, user: User, hardPity: HardPity, firstRarity5Acquired: Boolean): Pair<PullResult, Boolean> {
        val pullsTowardsPity = hardPity.pullsTowardsPity + 1
        val hardPityThreshold = 90
        val softPityThreshold = banner.softPity ?: 75
        val softPityIncrement = if (pullsTowardsPity >= softPityThreshold) 0.5 else 0.0

        val rarity5Items = banner.items.filter { it.rarity == "5" }
        val rarity4Items = banner.items.filter { it.rarity == "4" }
        val rarity3Items = banner.items.filter { it.rarity == "3" }

        val randomValue = Random.nextDouble(100.0)
        val rarity5Chance = 0.6 + softPityIncrement
        val rarity4Chance = 5.1
        val rarity3Chance = 100.0 - rarity5Chance - rarity4Chance

        val item: BannerItem
        val rarity: Int
        var newFirstRarity5Acquired = firstRarity5Acquired

        if (pullsTowardsPity >= hardPityThreshold || randomValue < rarity5Chance) {
            rarity = 5
            item = if (!firstRarity5Acquired || Random.nextBoolean()) {
                rarity5Items.first()
            } else {
                rarity5Items.random()
            }
            newFirstRarity5Acquired = true
        } else if (randomValue < rarity5Chance + rarity4Chance) {
            rarity = 4
            item = rarity4Items.random()
        } else {
            rarity = 3
            item = rarity3Items.random()
        }

        return PullResult(item, rarity) to newFirstRarity5Acquired
    }
}*//*



package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.UserModel
import com.visprog.starchive.views.PullResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class PullsimViewModel(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel
) : ViewModel() {
    private val _results = MutableStateFlow<List<PullResult>>(emptyList())
    val results: StateFlow<List<PullResult>> = _results

    private val _hardPityModel = MutableStateFlow(HardPityModel(1, 1, 1, 1, 0))
    val hardPityModel: StateFlow<HardPityModel> = _hardPityModel

    private val _firstRarityAcquired = MutableStateFlow(false)
    val firstRarityAcquired: StateFlow<Boolean> = _firstRarityAcquired

    fun simulatePull(bannerModel: BannerModel, userModel: UserModel) {
        viewModelScope.launch {
            val (result, newFirstRarityAcquired) = simulatePull(bannerModel, userModel, _hardPityModel.value, _firstRarityAcquired.value)
            _results.value = _results.value + result
            _firstRarityAcquired.value = newFirstRarityAcquired
            pullsimHistoryViewModel.addPullResult(result)
            if (result.rarity == bannerModel.items.maxOf { it.rarity.toInt() }) {
                _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = 0)
            } else {
                _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1)
            }
        }
    }

    fun simulateMultiplePulls(bannerModel: BannerModel, userModel: UserModel, numberOfPulls: Int) {
        viewModelScope.launch {
            var newFirstRarityAcquired = _firstRarityAcquired.value
            repeat(numberOfPulls) {
                val (result, updatedFirstRarityAcquired) = simulatePull(bannerModel, userModel, _hardPityModel.value, newFirstRarityAcquired)
                _results.value = _results.value + result
                newFirstRarityAcquired = updatedFirstRarityAcquired
                pullsimHistoryViewModel.addPullResult(result)
                if (result.rarity == bannerModel.items.maxOf { it.rarity.toInt() }) {
                    _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = 0)
                } else {
                    _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1)
                }
            }
            _firstRarityAcquired.value = newFirstRarityAcquired
        }
    }

    private fun simulatePull(bannerModel: BannerModel, userModel: UserModel, hardPityModel: HardPityModel, firstRarityAcquired: Boolean): Pair<PullResult, Boolean> {
        val pullsTowardsPity = hardPityModel.pullsTowardsPity + 1
        val hardPityThreshold = 90
        val softPityThreshold = bannerModel.softPity ?: 75
        val softPityIncrement = if (pullsTowardsPity >= softPityThreshold) 0.5 else 0.0

        val highestRarity = bannerModel.items.maxOf { it.rarity.toInt() }
        val rarityItems = bannerModel.items.groupBy { it.rarity.toInt() }

        val randomValue = Random.nextDouble(100.0)
        val highestRarityChance = 0.6 + softPityIncrement
        val secondHighestRarityChance = 5.1
        val lowestRarityChance = 100.0 - highestRarityChance - secondHighestRarityChance

        val item: BannerItemModel
        val rarity: Int
        var newFirstRarityAcquired = firstRarityAcquired

        if (pullsTowardsPity >= hardPityThreshold || randomValue < highestRarityChance) {
            rarity = highestRarity
            item = if (!firstRarityAcquired || Random.nextBoolean()) {
                rarityItems[highestRarity]!!.first()
            } else {
                rarityItems[highestRarity]!!.random()
            }
            newFirstRarityAcquired = true
        } else if (randomValue < highestRarityChance + secondHighestRarityChance) {
            rarity = highestRarity - 1
            item = rarityItems[rarity]!!.random()
        } else {
            rarity = highestRarity - 2
            item = rarityItems[rarity]!!.random()
        }

        return PullResult(item, rarity) to newFirstRarityAcquired
    }
    fun getHighestRarityChance(bannerModel: BannerModel): Double {
        val pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1
        val softPityThreshold = bannerModel.softPity ?: 75
        val softPityIncrement = if (pullsTowardsPity >= softPityThreshold) 0.5 else 0.0
        return 0.6 + softPityIncrement
    }
}
*/


package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.UserModel
import com.visprog.starchive.views.PullResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/*class PullsimViewModel(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel
) : ViewModel() {
    private val _results = MutableStateFlow<List<PullResult>>(emptyList())
    val results: StateFlow<List<PullResult>> = _results

    private val _hardPityModel = MutableStateFlow(HardPityModel(1, 1, 1, 1, 90, 0))
    val hardPityModel: StateFlow<HardPityModel> = _hardPityModel

    private val _firstRarityAcquired = MutableStateFlow(false)
    val firstRarityAcquired: StateFlow<Boolean> = _firstRarityAcquired*/

class PullsimViewModel(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel,
    hardPityModel: HardPityModel
) : ViewModel() {
    private val _results = MutableStateFlow<List<PullResult>>(emptyList())
    val results: StateFlow<List<PullResult>> = _results

    private val _hardPityModel = MutableStateFlow(hardPityModel)
    val hardPityModel: StateFlow<HardPityModel> = _hardPityModel

    private val _firstRarityAcquired = MutableStateFlow(false)
    val firstRarityAcquired: StateFlow<Boolean> = _firstRarityAcquired



    fun simulatePull(bannerModel: BannerModel, userModel: UserModel) {
        viewModelScope.launch {
            val (result, newFirstRarityAcquired) = simulatePull(bannerModel, userModel, _hardPityModel.value, _firstRarityAcquired.value)
            _results.value = _results.value + result
            _firstRarityAcquired.value = newFirstRarityAcquired
            pullsimHistoryViewModel.addPullResult(result)
            if (result.rarity == bannerModel.items.maxOf { it.rarity.toInt() }) {
                _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = 0)
            } else {
                _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1)
            }
        }
    }

    fun simulateMultiplePulls(bannerModel: BannerModel, userModel: UserModel, numberOfPulls: Int) {
        viewModelScope.launch {
            var newFirstRarityAcquired = _firstRarityAcquired.value
            repeat(numberOfPulls) {
                val (result, updatedFirstRarityAcquired) = simulatePull(bannerModel, userModel, _hardPityModel.value, newFirstRarityAcquired)
                _results.value = _results.value + result
                newFirstRarityAcquired = updatedFirstRarityAcquired
                pullsimHistoryViewModel.addPullResult(result)
                if (result.rarity == bannerModel.items.maxOf { it.rarity.toInt() }) {
                    _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = 0)
                } else {
                    _hardPityModel.value = _hardPityModel.value.copy(pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1)
                }
            }
            _firstRarityAcquired.value = newFirstRarityAcquired
        }
    }

    private fun simulatePull(bannerModel: BannerModel, userModel: UserModel, hardPityModel: HardPityModel, firstRarityAcquired: Boolean): Pair<PullResult, Boolean> {
        Log.d("PullsimViewModel", "pityThreshold: ${hardPityModel.pityThreshold}")
        val pullsTowardsPity = hardPityModel.pullsTowardsPity + 1
        val hardPityThreshold = hardPityModel.pityThreshold ?: 90
        val softPityThreshold = bannerModel.softPity ?: 75
        val softPityIncrement = if (pullsTowardsPity >= softPityThreshold) 0.5 else 0.0

        val highestRarity = bannerModel.items.maxOf { it.rarity.toInt() }
        val rarityItems = bannerModel.items.groupBy { it.rarity.toInt() }

        val randomValue = Random.nextDouble(100.0)
        val highestRarityChance = 0.6 + softPityIncrement
        val secondHighestRarityChance = 5.1
        val lowestRarityChance = 100.0 - highestRarityChance - secondHighestRarityChance

        val item: BannerItemModel
        val rarity: Int
        var newFirstRarityAcquired = firstRarityAcquired

        if (pullsTowardsPity >= hardPityThreshold || randomValue < highestRarityChance) {
            rarity = highestRarity
            item = if (!firstRarityAcquired || Random.nextBoolean()) {
                rarityItems[highestRarity]!!.first()
            } else {
                rarityItems[highestRarity]!!.random()
            }
            newFirstRarityAcquired = true
        } else if (randomValue < highestRarityChance + secondHighestRarityChance) {
            rarity = highestRarity - 1
            item = rarityItems[rarity]!!.random()
        } else {
            rarity = highestRarity - 2
            item = rarityItems[rarity]!!.random()
        }

        return PullResult(item, rarity) to newFirstRarityAcquired
    }

    fun getHighestRarityChance(bannerModel: BannerModel): Double {
        val pullsTowardsPity = _hardPityModel.value.pullsTowardsPity + 1
        val softPityThreshold = bannerModel.softPity ?: 75
        val softPityIncrement = if (pullsTowardsPity >= softPityThreshold) 0.5 else 0.0
        return 0.6 + softPityIncrement
    }
}