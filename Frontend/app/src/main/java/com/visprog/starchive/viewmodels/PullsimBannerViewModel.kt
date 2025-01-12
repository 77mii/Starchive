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
import com.visprog.starchive.models.ItemModel
import com.visprog.starchive.models.ItemResponse
import com.visprog.starchive.repositories.BannerItemRepository
import com.visprog.starchive.repositories.ItemRepository
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

class PullsimBannerViewModel(
    private val bannerItemRepository: BannerItemRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _bannerItems = MutableStateFlow<List<Pair<BannerItemModel, ItemModel?>>>(emptyList())
    val bannerItems: StateFlow<List<Pair<BannerItemModel, ItemModel?>>> = _bannerItems

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
                                    itemRepository.getItemById(tokenValue, bannerItem.itemId).enqueue(object : Callback<ItemResponse> {
                                        override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
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

                                        override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val bannerItemRepository = application.container.bannerItemRepository
                val itemRepository = application.container.itemRepository
                val userRepository = application.container.userRepository
                PullsimBannerViewModel(bannerItemRepository, itemRepository, userRepository)
            }
        }
    }
}