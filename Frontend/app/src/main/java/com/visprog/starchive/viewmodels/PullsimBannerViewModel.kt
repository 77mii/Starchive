package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.ItemModel
import com.visprog.starchive.models.UserModel
import com.visprog.starchive.repositories.BannerItemRepository
import com.visprog.starchive.repositories.BannerRepository
import com.visprog.starchive.repositories.ItemRepository
import com.visprog.starchive.repositories.PityRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.services.BannerItemResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class PullsimBannerViewModel(
    private val bannerItemRepository: BannerItemRepository,
    private val bannerRepository: BannerRepository,
    private val itemRepository: ItemRepository,
    private val pityRepository: PityRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _bannerItems = MutableStateFlow<List<Pair<BannerItemModel, ItemModel?>>>(emptyList())
    val bannerItems: StateFlow<List<Pair<BannerItemModel, ItemModel?>>> = _bannerItems

    private val _banner = MutableStateFlow<BannerModel?>(null)
    val banner: StateFlow<BannerModel?> = _banner

    private val _pity = MutableStateFlow<HardPityModel?>(null)
    val pity: StateFlow<HardPityModel?> = _pity

    private val _gachaResults = MutableStateFlow<List<ItemModel>>(emptyList())
    val gachaResults: StateFlow<List<ItemModel>> = _gachaResults

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun getBannerItems(bannerId: Int) {
        viewModelScope.launch {
            token.collect { tokenValue ->
                if (tokenValue.isNotEmpty()) {
                    bannerItemRepository.getBannerItemsByBannerId(tokenValue, bannerId).enqueue(object : Callback<BannerItemResponse> {
                        override fun onResponse(call: Call<BannerItemResponse>, response: Response<BannerItemResponse>) {
                            if (response.isSuccessful) {
                                Log.d("PullsimBannerViewModel", "Raw banner items data: ${response.body()}")
                                val bannerItems = response.body()?.data ?: emptyList()
                                val bannerItemsWithDetails = mutableListOf<Pair<BannerItemModel, ItemModel?>>()

                                bannerItems.forEach { bannerItem ->
                                    itemRepository.getItemById(tokenValue, bannerItem.itemId).enqueue(object : Callback<GeneralDataResponse<ItemModel>> {
                                        override fun onResponse(call: Call<GeneralDataResponse<ItemModel>>, response: Response<GeneralDataResponse<ItemModel>>) {
                                            if (response.isSuccessful) {
                                                val item = response.body()?.data
                                                Log.d("PullsimBannerViewModel", "Raw item data: ${response.body()}")
                                                bannerItemsWithDetails.add(Pair(bannerItem, item))
                                                _bannerItems.value = bannerItemsWithDetails
                                                Log.d("PullsimBannerViewModel", "Item data received: $item for BannerItem: $bannerItem")
                                            } else {
                                                Log.e("PullsimBannerViewModel", "Item response not successful: ${response.errorBody()?.string()}")
                                            }
                                        }

                                        override fun onFailure(call: Call<GeneralDataResponse<ItemModel>>, t: Throwable) {
                                            Log.e("PullsimBannerViewModel", "Item request failed", t)
                                        }
                                    })
                                }
                                Log.d("PullsimBannerViewModel", "BannerItems data received: $bannerItems")
                            } else {
                                Log.e("PullsimBannerViewModel", "BannerItem response not successful: ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<BannerItemResponse>, t: Throwable) {
                            Log.e("PullsimBannerViewModel", "BannerItem request failed", t)
                        }
                    })
                }
            }
        }
    }

    fun getBanner(bannerId: Int) {
        viewModelScope.launch {
            token.collect { tokenValue ->
                if (tokenValue.isNotEmpty()) {
                    bannerRepository.getBannerById(tokenValue, bannerId).enqueue(object : Callback<GeneralDataResponse<BannerModel>> {
                        override fun onResponse(call: Call<GeneralDataResponse<BannerModel>>, response: Response<GeneralDataResponse<BannerModel>>) {
                            if (response.isSuccessful) {
                                val banner = response.body()?.data
                                Log.d("PullsimBannerViewModel", "Banner data received: $banner")
                                _banner.value = banner
                            } else {
                                Log.e("PullsimBannerViewModel", "Banner response not successful: ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<GeneralDataResponse<BannerModel>>, t: Throwable) {
                            Log.e("PullsimBannerViewModel", "Banner request failed", t)
                        }
                    })
                }
            }
        }
    }

    fun getPity(bannerId: Int) {
        viewModelScope.launch {
            token.collect { tokenValue ->
                if (tokenValue.isNotEmpty()) {
                    pityRepository.getPityByBannerIdAndToken(tokenValue, bannerId, tokenValue).enqueue(object : Callback<GeneralDataResponse<HardPityModel>> {
                        override fun onResponse(call: Call<GeneralDataResponse<HardPityModel>>, response: Response<GeneralDataResponse<HardPityModel>>) {
                            if (response.isSuccessful) {
                                val pity = response.body()?.data
                                Log.d("PullsimBannerViewModel", "Pity data received: $pity")
                                _pity.value = pity
                            } else {
                                Log.e("PullsimBannerViewModel", "Pity response not successful: ${response.errorBody()?.string()}")
                                // Create a new HardPityModel entry if not found
                                createNewPity(bannerId, tokenValue)
                            }
                        }

                        override fun onFailure(call: Call<GeneralDataResponse<HardPityModel>>, t: Throwable) {
                            Log.e("PullsimBannerViewModel", "Pity request failed", t)
                        }
                    })
                }
            }
        }
    }

    private fun createNewPity(bannerId: Int, tokenValue: String) {
        viewModelScope.launch {
            userRepository.getUserIdByToken(tokenValue).enqueue(object : Callback<GeneralDataResponse<UserModel>> {
                override fun onResponse(call: Call<GeneralDataResponse<UserModel>>, response: Response<GeneralDataResponse<UserModel>>) {
                    if (response.isSuccessful) {
                        val user = response.body()?.data
                        val newPity = HardPityModel(
                            pityId = 0, // This will be auto-generated by the backend
                            userId = user?.userId ?: 0,
                            gameId = _banner.value?.gameId ?: 0,
                            bannerId = bannerId,
                            pullTowardsPity = 0
                        )
                        pityRepository.createPity(tokenValue, newPity).enqueue(object : Callback<GeneralDataResponse<HardPityModel>> {
                            override fun onResponse(call: Call<GeneralDataResponse<HardPityModel>>, response: Response<GeneralDataResponse<HardPityModel>>) {
                                if (response.isSuccessful) {
                                    val pity = response.body()?.data
                                    Log.d("PullsimBannerViewModel", "New pity data created: $pity")
                                    _pity.value = pity
                                } else {
                                    Log.e("PullsimBannerViewModel", "Failed to create new pity data: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<GeneralDataResponse<HardPityModel>>, t: Throwable) {
                                Log.e("PullsimBannerViewModel", "Failed to create new pity data", t)
                            }
                        })
                    } else {
                        Log.e("PullsimBannerViewModel", "Failed to get user by token: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<GeneralDataResponse<UserModel>>, t: Throwable) {
                    Log.e("PullsimBannerViewModel", "Failed to get user by token", t)
                }
            })
        }
    }

    fun performGachaPull(bannerId: Int) {
        viewModelScope.launch {
            token.collect { tokenValue ->
                if (tokenValue.isNotEmpty()) {
                    val currentPity = _pity.value?.pullTowardsPity ?: 0
                    val bannerItemsWithDetails = _bannerItems.value
                    val gachaResults = mutableListOf<ItemModel>()
                    var highestRarityAcquired = false

                    repeat(10) { pullIndex ->
                        val randomValue = Random.nextFloat()
                        val selectedItem = if (currentPity >= 90 && !highestRarityAcquired) {
                            // Guaranteed rare item due to pity, but only once
                            highestRarityAcquired = true
                            bannerItemsWithDetails.filter { it.second?.rarity == bannerItemsWithDetails.maxOfOrNull { it.second?.rarity ?: 0 } }.randomOrNull()?.second
                        } else {
                            // Regular gacha pull with dynamic rarity
                            val cumulativeRates = bannerItemsWithDetails.map { it.first.acquireRate }.runningReduce { acc, rate -> acc + rate }
                            val selectedIndex = cumulativeRates.indexOfFirst { it >= randomValue }
                            bannerItemsWithDetails.getOrNull(selectedIndex)?.second
                        }

                        selectedItem?.let { 
                            gachaResults.add(it)
                        }
                    }

                    // Clear old gacha results and add new ones
                    _gachaResults.value = emptyList()
                    _gachaResults.value = gachaResults

                    // Update pity count
                    val newPityCount = if (highestRarityAcquired) 0 else currentPity + 10
                    _pity.value = _pity.value?.copy(pullTowardsPity = newPityCount)

                    // Update pity data on the backend
                    _pity.value?.let { pity ->
                        Log.d("PullsimBannerViewModel", "Updating pity data with new count: $newPityCount")
                        pityRepository.updatePity(tokenValue, pity.pityId, pity.copy(pullTowardsPity = newPityCount)).enqueue(object : Callback<GeneralDataResponse<HardPityModel>> {
                            override fun onResponse(call: Call<GeneralDataResponse<HardPityModel>>, response: Response<GeneralDataResponse<HardPityModel>>) {
                                if (response.isSuccessful) {
                                    Log.d("PullsimBannerViewModel", "Pity data updated successfully: ${response.body()}")
                                } else {
                                    Log.e("PullsimBannerViewModel", "Failed to update pity data: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<GeneralDataResponse<HardPityModel>>, t: Throwable) {
                                Log.e("PullsimBannerViewModel", "Failed to update pity data", t)
                            }
                        })
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val bannerItemRepository = application.container.bannerItemRepository
                val bannerRepository = application.container.bannerRepository
                val itemRepository = application.container.itemRepository
                val pityRepository = application.container.pityRepository
                val userRepository = application.container.userRepository
                PullsimBannerViewModel(bannerItemRepository, bannerRepository, itemRepository, pityRepository, userRepository)
            }
        }
    }
}