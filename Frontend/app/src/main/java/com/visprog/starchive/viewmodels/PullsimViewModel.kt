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
}*/


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
            } else {
                _hardPity.value = _hardPity.value.copy(pullsTowardsPity = _hardPity.value.pullsTowardsPity + 1)
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
                } else {
                    _hardPity.value = _hardPity.value.copy(pullsTowardsPity = _hardPity.value.pullsTowardsPity + 1)
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
}