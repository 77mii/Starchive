package com.visprog.starchive

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.visprog.starchive.repositories.ArticleRepository
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.repositories.NetworkArticleRepository
import com.visprog.starchive.repositories.NetworkBudgetRepository
import com.visprog.starchive.repositories.NetworkUserRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.services.ArticleService
import com.visprog.starchive.services.BudgetService
import com.visprog.starchive.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val budgetRepository: BudgetRepository
    val articleRepository: ArticleRepository
}

class DefaultAppContainer(private val userDataStore: DataStore<Preferences>): AppContainer {
    // ip address
    private val baseUrl = "https://192.168.1.36/"

    // RETROFIT SERVICE
    private val userRetrofitService: UserService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserService::class.java)
    }

    private val budgetRetrofitService: BudgetService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(BudgetService::class.java)
    }

    private val articleRetrofitService: ArticleService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ArticleService::class.java)
    }

    // REPOSITORY INITIALIZATION
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val budgetRepository: BudgetRepository by lazy {
        NetworkBudgetRepository(budgetRetrofitService)
    }

    override val articleRepository: ArticleRepository by lazy {
        NetworkArticleRepository(articleRetrofitService)
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