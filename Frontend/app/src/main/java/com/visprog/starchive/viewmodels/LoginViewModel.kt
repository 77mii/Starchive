package com.visprog.starchive.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visprog.starchive.uiStates.AuthenticationStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    private val _loginState = MutableStateFlow<AuthenticationStatusUIState>(AuthenticationStatusUIState.Start)
    val loginState: StateFlow<AuthenticationStatusUIState> = _loginState

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthenticationStatusUIState.Loading
            val user = mainViewModel.dummyUser
            if (user.username == username && user.password == password) {
                _loginState.value = AuthenticationStatusUIState.Success(user)
            } else {
                _loginState.value = AuthenticationStatusUIState.Failed("Invalid credentials")
            }
        }
    }
}