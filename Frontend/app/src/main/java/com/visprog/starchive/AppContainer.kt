

package com.visprog.starchive

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.visprog.starchive.repositories.*
import com.visprog.starchive.services.*
import com.visprog.starchive.utils.LocalDateTimeDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

interface AppContainer {
    val userRepository: UserRepository
    val budgetRepository: BudgetRepository
    val articleRepository: ArticleRepository
    val authRepository: AuthRepository
    val bannerRepository: BannerRepository
    val userGamesRepository: UserGamesRepository
    val gameRepository: GameRepository
}

class DefaultAppContainer(private val userDataStore: DataStore<Preferences>): AppContainer {
    // ip address
    private val baseUrl = "http://10.0.2.2:3000/"

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
    private val gameRetrofitService: GameService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(GameService::class.java)
    }

    // REPOSITORY INITIALIZATION
    override val gameRepository: GameRepository by lazy {
        NetworkGameRepository(gameRetrofitService)
    }
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
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        val gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}