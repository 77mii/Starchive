// package com.visprog.starchive.viewmodels

// import android.util.Log
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.ViewModelProvider
// import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
// import androidx.lifecycle.viewModelScope
// import androidx.lifecycle.viewmodel.initializer
// import androidx.lifecycle.viewmodel.viewModelFactory
// import com.visprog.starchive.StarchiveApplication
// import com.visprog.starchive.models.BannerItemModel
// import com.visprog.starchive.models.GeneralDataResponse
// import com.visprog.starchive.models.HardPityModel
// import com.visprog.starchive.models.ItemModel
// import com.visprog.starchive.repositories.BannerItemRepository
// import com.visprog.starchive.repositories.ItemRepository
// import com.visprog.starchive.repositories.PityRepository
// import com.visprog.starchive.repositories.UserRepository
// import com.visprog.starchive.services.BannerItemResponse
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.SharingStarted
// import kotlinx.coroutines.flow.StateFlow
// import kotlinx.coroutines.flow.stateIn
// import kotlinx.coroutines.launch
// import retrofit2.Call
// import retrofit2.Callback
// import retrofit2.Response
// import kotlin.random.Random

// class PullsimBannerViewModel(
//     private val bannerItemRepository: BannerItemRepository,
//     private val itemRepository: ItemRepository,
//     private val pityRepository: PityRepository,
//     private val userRepository: UserRepository
// ) : ViewModel() {
//     private val _bannerItems = MutableStateFlow<List<Pair<BannerItemModel, ItemModel?>>>(emptyList())
//     val bannerItems: StateFlow<List<Pair<BannerItemModel, ItemModel?>>> = _bannerItems

//     private val _pity = MutableStateFlow<HardPityModel?>(null)
//     val pity: StateFlow<HardPityModel?> = _pity

//     private val _gachaResults = MutableStateFlow<List<ItemModel>>(emptyList())
//     val gachaResults: StateFlow<List<ItemModel>> = _gachaResults

//     val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
//         scope = viewModelScope,
//         started = SharingStarted.WhileSubscribed(5000),
//         initialValue = ""
//     )

//     fun getBannerItems(bannerId: Int) {
//         viewModelScope.launch {
//             token.collect { tokenValue ->
//                 if (tokenValue.isNotEmpty()) {
//                     bannerItemRepository.getBannerItemsByBannerId(tokenValue, bannerId).enqueue(object : Callback<BannerItemResponse> {
//                         override fun onResponse(call: Call<BannerItemResponse>, response: Response<BannerItemResponse>) {
//                             if (response.isSuccessful) {
//                                 Log.d("PullsimBannerViewModel", "Raw banner items data: ${response.body()}")
//                                 val bannerItems = response.body()?.data ?: emptyList()
//                                 val bannerItemsWithDetails = mutableListOf<Pair<BannerItemModel, ItemModel?>>()

//                                 bannerItems.forEach { bannerItem ->
//                                     itemRepository.getItemById(tokenValue, bannerItem.itemId).enqueue(object : Callback<GeneralDataResponse<ItemModel>> {
//                                         override fun onResponse(call: Call<GeneralDataResponse<ItemModel>>, response: Response<GeneralDataResponse<ItemModel>>) {
//                                             if (response.isSuccessful) {
//                                                 val item = response.body()?.data
//                                                 Log.d("PullsimBannerViewModel", "Raw item data: ${response.body()}")
//                                                 bannerItemsWithDetails.add(Pair(bannerItem, item))
//                                                 _bannerItems.value = bannerItemsWithDetails
//                                                 Log.d("PullsimBannerViewModel", "Item data received: $item for BannerItem: $bannerItem")
//                                             } else {
//                                                 Log.e("PullsimBannerViewModel", "Item response not successful: ${response.errorBody()?.string()}")
//                                             }
//                                         }

//                                         override fun onFailure(call: Call<GeneralDataResponse<ItemModel>>, t: Throwable) {
//                                             Log.e("PullsimBannerViewModel", "Item request failed", t)
//                                         }
//                                     })
//                                 }
//                                 Log.d("PullsimBannerViewModel", "BannerItems data received: $bannerItems")
//                             } else {
//                                 Log.e("PullsimBannerViewModel", "BannerItem response not successful: ${response.errorBody()?.string()}")
//                             }
//                         }

//                         override fun onFailure(call: Call<BannerItemResponse>, t: Throwable) {
//                             Log.e("PullsimBannerViewModel", "BannerItem request failed", t)
//                         }
//                     })
//                 }
//             }
//         }
//     }

//     fun getPity(bannerId: Int) {
//         viewModelScope.launch {
//             token.collect { tokenValue ->
//                 if (tokenValue.isNotEmpty()) {
//                     pityRepository.getPityByBannerIdAndToken(tokenValue, bannerId, tokenValue).enqueue(object : Callback<GeneralDataResponse<HardPityModel>> {
//                         override fun onResponse(call: Call<GeneralDataResponse<HardPityModel>>, response: Response<GeneralDataResponse<HardPityModel>>) {
//                             if (response.isSuccessful) {
//                                 val pity = response.body()?.data
//                                 Log.d("PullsimBannerViewModel", "Pity data received: $pity")
//                                 _pity.value = pity
//                             } else {
//                                 Log.e("PullsimBannerViewModel", "Pity response not successful: ${response.errorBody()?.string()}")
//                             }
//                         }

//                         override fun onFailure(call: Call<GeneralDataResponse<HardPityModel>>, t: Throwable) {
//                             Log.e("PullsimBannerViewModel", "Pity request failed", t)
//                         }
//                     })
//                 }
//             }
//         }
//     }

//     fun performGachaPull(bannerId: Int) {
//         viewModelScope.launch {
//             token.collect { tokenValue ->
//                 if (tokenValue.isNotEmpty()) {
//                     val currentPity = _pity.value?.pullTowardsPity ?: 0
//                     val bannerItemsWithDetails = _bannerItems.value
//                     val gachaResults = mutableListOf<ItemModel>()

//                     repeat(10) {
//                         val randomValue = Random.nextFloat()
//                         val selectedItem = if (currentPity >= 90 || randomValue <= 0.01) {
//                             // Guaranteed rare item due to pity or luck
//                             bannerItemsWithDetails.filter { it.second?.rarity == bannerItemsWithDetails.maxOfOrNull { it.second?.rarity ?: 0 } }.randomOrNull()?.second
//                         } else {
//                             // Regular gacha pull with dynamic rarity
//                             val cumulativeRates = bannerItemsWithDetails.map { it.first.acquireRate }.scan(0.0) { acc, rate -> acc + rate }
//                             val selectedIndex = cumulativeRates.indexOfFirst { it >= randomValue }
//                             bannerItemsWithDetails.getOrNull(selectedIndex)?.second
//                         }

//                         selectedItem?.let { gachaResults.add(it) }
//                     }

//                     _gachaResults.value = gachaResults

//                     // Update pity count
//                     val newPityCount = if (gachaResults.any { it.rarity == bannerItemsWithDetails.maxOfOrNull { it.second?.rarity ?: 0 } }) 0 else currentPity + 10
//                     _pity.value = _pity.value?.copy(pullTowardsPity = newPityCount)
//                 }
//             }
//         }
//     }

//     companion object {
//         val Factory: ViewModelProvider.Factory = viewModelFactory {
//             initializer {
//                 val application = (this[APPLICATION_KEY] as StarchiveApplication)
//                 val bannerItemRepository = application.container.bannerItemRepository
//                 val itemRepository = application.container.itemRepository
//                 val pityRepository = application.container.pityRepository
//                 val userRepository = application.container.userRepository
//                 PullsimBannerViewModel(bannerItemRepository, itemRepository, pityRepository, userRepository)
//             }
//         }
//     }
// }

package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.BannerItemModel
import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.models.ItemModel
import com.visprog.starchive.repositories.BannerItemRepository
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
    private val itemRepository: ItemRepository,
    private val pityRepository: PityRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _bannerItems = MutableStateFlow<List<Pair<BannerItemModel, ItemModel?>>>(emptyList())
    val bannerItems: StateFlow<List<Pair<BannerItemModel, ItemModel?>>> = _bannerItems

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

    fun performGachaPull(bannerId: Int) {
        viewModelScope.launch {
            token.collect { tokenValue ->
                if (tokenValue.isNotEmpty()) {
                    val currentPity = _pity.value?.pullTowardsPity ?: 0
                    val bannerItemsWithDetails = _bannerItems.value
                    val gachaResults = mutableListOf<ItemModel>()

                    repeat(10) {
                        val randomValue = Random.nextFloat()
                        val selectedItem = if (currentPity >= 90 || randomValue <= 0.01) {
                            // Guaranteed rare item due to pity or luck
                            bannerItemsWithDetails.filter { it.second?.rarity == bannerItemsWithDetails.maxOfOrNull { it.second?.rarity ?: 0 } }.randomOrNull()?.second
                        } else {
                            // Regular gacha pull with dynamic rarity
                            val cumulativeRates = bannerItemsWithDetails.map { it.first.acquireRate }.scan(0.0) { acc, rate -> acc + rate }
                            val selectedIndex = cumulativeRates.indexOfFirst { it >= randomValue }
                            bannerItemsWithDetails.getOrNull(selectedIndex)?.second
                        }

                        selectedItem?.let { gachaResults.add(it) }
                    }

                    _gachaResults.value = gachaResults

                    // Update pity count
                    val newPityCount = if (gachaResults.any { it.rarity == bannerItemsWithDetails.maxOfOrNull { it.second?.rarity ?: 0 } }) 0 else currentPity + 10
                    _pity.value = _pity.value?.copy(pullTowardsPity = newPityCount)

                    // Update pity data on the backend
                    _pity.value?.let { pity ->
                        pityRepository.updatePity(tokenValue, pity.pityId, pity.copy(pullTowardsPity = newPityCount)).enqueue(object : Callback<GeneralDataResponse<HardPityModel>> {
                            override fun onResponse(call: Call<GeneralDataResponse<HardPityModel>>, response: Response<GeneralDataResponse<HardPityModel>>) {
                                if (response.isSuccessful) {
                                    Log.d("PullsimBannerViewModel", "Pity data updated successfully")
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
                val itemRepository = application.container.itemRepository
                val pityRepository = application.container.pityRepository
                val userRepository = application.container.userRepository
                PullsimBannerViewModel(bannerItemRepository, itemRepository, pityRepository, userRepository)
            }
        }
    }
}