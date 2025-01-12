package com.visprog.starchive.repositories

import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.ItemModel
import com.visprog.starchive.services.ItemService
import retrofit2.Call

interface ItemRepository {
    fun getItemById(token: String, itemId: Int): Call<GeneralDataResponse<ItemModel>>
}

class NetworkItemRepository(
    private val itemService: ItemService
) : ItemRepository {

    override fun getItemById(token: String, itemId: Int): Call<GeneralDataResponse<ItemModel>> {
        return itemService.getItemById(token, itemId)
    }
}