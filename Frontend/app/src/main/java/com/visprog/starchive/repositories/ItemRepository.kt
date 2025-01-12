package com.visprog.starchive.repositories

import com.visprog.starchive.models.ItemResponse
import com.visprog.starchive.services.ItemService
import retrofit2.Call

interface ItemRepository {
    fun getItemById(token: String, itemId: Int): Call<ItemResponse>
}

class NetworkItemRepository(
    private val itemService: ItemService
) : ItemRepository {

    override fun getItemById(token: String, itemId: Int): Call<ItemResponse> {
        return itemService.getItemById(token, itemId)
    }
}