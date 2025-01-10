package com.visprog.starchive

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.visprog.starchive.repositories.ArticleRepository
import com.visprog.starchive.repositories.AuthRepository
import com.visprog.starchive.repositories.BannerRepository
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.repositories.NetworkArticleRepository
import com.visprog.starchive.repositories.NetworkAuthRepository
import com.visprog.starchive.repositories.NetworkBannerRepository
import com.visprog.starchive.repositories.NetworkBudgetRepository
import com.visprog.starchive.repositories.NetworkUserGamesRepository
import com.visprog.starchive.repositories.NetworkUserRepository
import com.visprog.starchive.repositories.UserGamesRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.services.ArticleService
import com.visprog.starchive.services.AuthAPIService
import com.visprog.starchive.services.BannerService
import com.visprog.starchive.services.BudgetService
import com.visprog.starchive.services.UserGameService
import com.visprog.starchive.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val budgetRepository: BudgetRepository
    val articleRepository: ArticleRepository
    val authRepository: AuthRepository
    val bannerRepository: BannerRepository
    val userGamesRepository: UserGamesRepository
}

class DefaultAppContainer(private val userDataStore: DataStore<Preferences>): AppContainer {
    // ip address
    private val baseUrl = "http://192.168.1.11:3000/"

    // RETROFIT SERVICE
    private val userRetrofitService: UserService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserService::class.java)
    }
    private val authenticationRetrofitService: AuthAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthAPIService::class.java)
    }

    private val budgetRetrofitService: BudgetService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(BudgetService::class.java)
    }

    private val articleRetrofitService: ArticleService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ArticleService::class.java)
    }

    private val bannerRetrofitService: BannerService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(BannerService::class.java)
    }

    private val userGameRetrofitService: UserGameService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserGameService::class.java)
    }

    // REPOSITORY INITIALIZATION
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val bannerRepository: BannerRepository by lazy {
        NetworkBannerRepository(bannerRetrofitService)
    }

    override val userGamesRepository: UserGamesRepository by lazy {
        NetworkUserGamesRepository(userGameRetrofitService)
    }

    override val budgetRepository: BudgetRepository by lazy {
        NetworkBudgetRepository(budgetRetrofitService)
    }

    override val articleRepository: ArticleRepository by lazy {
        NetworkArticleRepository(articleRetrofitService)
    }
    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(authenticationRetrofitService)
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