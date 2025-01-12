package com.visprog.starchive.services

import com.visprog.starchive.models.ItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ItemService {
    @GET("/api/itemsbyitemid/{itemId}")
    fun getItemById(
        @Header("X-API-TOKEN") token: String,
        @Path("itemId") itemId: Int
    ): Call<ItemResponse>
}