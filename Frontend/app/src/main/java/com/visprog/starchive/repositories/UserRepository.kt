package com.visprog.starchive.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.UserModel

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

    fun logout(token: String): Call<GeneralResponseModel>

    suspend fun saveUserToken(token: String)

    suspend fun saveUsername(username: String)

    fun getUserIdByToken(token: String): Call<GeneralDataResponse<UserModel>>
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userService: UserService
): UserRepository {
    
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Unknown"
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userService.logout(token)
    }

    override fun getUserIdByToken(token: String): Call<GeneralDataResponse<UserModel>> {
        return userService.getUserIdByToken(token, token)
    }
}