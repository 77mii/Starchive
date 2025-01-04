package com.visprog.starchive

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.visprog.starchive.repositories.NetworkUserRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
}

class DefaultAppContainer(private val userDataStore: DataStore<Preferences>): AppContainer {
    // ip address
    private val baseUrl = "https://182.253.122.0/"

    // RETROFIT SERVICE
    private val userRetrofitService: UserService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserService::class.java)
    }

    // REPOSITORY INITIALIZATION
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }

}