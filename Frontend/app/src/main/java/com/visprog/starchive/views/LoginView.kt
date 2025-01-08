/*


package com.visprog.starchive.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.visprog.starchive.viewmodels.LoginViewModel
import com.visprog.starchive.viewmodels.MainViewModel
import com.visprog.starchive.uistates.AuthenticationStatusUIState
import com.visprog.starchive.viewmodels.LoginViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginView(mainViewModel: MainViewModel, navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(mainViewModel))
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { loginViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        when (loginState) {
            is AuthenticationStatusUIState.Success -> {
                navController.navigate("gameSelection")
            }
            is AuthenticationStatusUIState.Failed -> {
                Text("Error: ${(loginState as AuthenticationStatusUIState.Failed).errorMessage}")
            }
            is AuthenticationStatusUIState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {}
        }
    }
}*/

package com.visprog.starchive.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.visprog.starchive.viewmodels.LoginViewModel
import com.visprog.starchive.viewmodels.MainViewModel
import com.visprog.starchive.uiStates.AuthenticationStatusUIState
import com.visprog.starchive.viewmodels.LoginViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginView(mainViewModel: MainViewModel, navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(mainViewModel))
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { loginViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        when (loginState) {
            is AuthenticationStatusUIState.Success -> {
                navController.navigate("gameSelection")
            }
            is AuthenticationStatusUIState.Failed -> {
                Text("Error: ${(loginState as AuthenticationStatusUIState.Failed).errorMessage}")
            }
            is AuthenticationStatusUIState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {}
        }
    }
}
