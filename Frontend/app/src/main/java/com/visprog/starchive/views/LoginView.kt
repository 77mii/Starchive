package com.visprog.starchive.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.visprog.starchive.R
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.uiStates.AuthenticationStatusUIState
import com.visprog.starchive.viewmodels.AuthViewModel
import com.visprog.starchive.views.templates.AuthenticationButton
import com.visprog.starchive.views.templates.AuthenticationOutlinedTextField
import com.visprog.starchive.views.templates.AuthenticationQuestion
import com.visprog.starchive.views.templates.PasswordOutlinedTextField
import com.visprog.starchive.views.templates.CommonTemplateWithoutNavBar
import com.visprog.starchive.ui.theme.Cream

@Composable
fun LoginView(
    authenticationViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context
) {
    val loginUIState by authenticationViewModel.authenticationUIState.collectAsState()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(authenticationViewModel.dataStatus) {
        val dataStatus = authenticationViewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            authenticationViewModel.clearErrorMessage()
        }
    }

    CommonTemplateWithoutNavBar(
        currentScreen = "Login",
        gameId = 0,
        onNavigate = { _, _ -> }
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "WELCOME BACK TO",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Light,
                    color = Cream
                )

                Text(
                    text = "TODO LIST",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Cream
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AuthenticationOutlinedTextField(
                    inputValue = authenticationViewModel.usernameInput,
                    onInputValueChange = {
                        authenticationViewModel.changeUsernameInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = stringResource(id = R.string.usernameText),
                    placeholderText = stringResource(id = R.string.usernameText),
                    leadingIconSrc = painterResource(id = R.drawable.ic_username),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                Spacer(modifier = Modifier.padding(5.dp))

                PasswordOutlinedTextField(
                    passwordInput = authenticationViewModel.passwordInput,
                    onPasswordInputValueChange = {
                        authenticationViewModel.changePasswordInput(it)
                        authenticationViewModel.checkLoginForm()
                    },
                    passwordVisibilityIcon = painterResource(id = loginUIState.passwordVisibilityIcon),
                    labelText = stringResource(id = R.string.passwordText),
                    placeholderText = stringResource(id = R.string.passwordText),
                    onTrailingIconClick = {
                        authenticationViewModel.changePasswordVisibility()
                    },
                    passwordVisibility = loginUIState.passwordVisibility,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardImeAction = ImeAction.None,
                    onKeyboardNext = KeyboardActions(
                        onDone = null
                    )
                )

                AuthenticationButton(
                    buttonText = stringResource(id = R.string.loginText),
                    onButtonClick = {
                        authenticationViewModel.loginUser(navController = navController)
                    },
                    buttonModifier = Modifier
                        .padding(top = 30.dp),
                    textModifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    buttonEnabled = loginUIState.buttonEnabled,
                    buttonColor = authenticationViewModel.checkButtonEnabled(loginUIState.buttonEnabled),
                    userDataStatusUIState = authenticationViewModel.dataStatus,
                    loadingBarModifier = Modifier
                        .padding(top = 30.dp)
                        .size(40.dp)
                )
            }

            AuthenticationQuestion(
                questionText = stringResource(id = R.string.don_t_have_an_account_yet_text),
                actionText = stringResource(id = R.string.sign_up_text),
                onActionTextClicked = {
                    authenticationViewModel.resetViewModel()
                    navController.navigate(PagesEnum.Signup.name) {
                        popUpTo(PagesEnum.Login.name) {
                            inclusive = true
                        }
                    }
                },
                rowModifier = Modifier
                    .align(Alignment.CenterHorizontally),

            )
        }
    }
}

@Preview(
showSystemUi = true,
showBackground = true
)
@Composable
fun LoginViewPreview() {
    StarchiveTheme {
        LoginView(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            authenticationViewModel = viewModel(),
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }
}
