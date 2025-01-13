package com.visprog.starchive.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visprog.starchive.R
import com.visprog.starchive.uiStates.AuthenticationStatusUIState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.ui.theme.Cream
import com.visprog.starchive.ui.theme.DarkBlue

@Composable
fun AuthenticationOutlinedTextField(
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String,
    leadingIconSrc: Painter,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardOptions,
    onKeyboardNext: KeyboardActions
) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = onInputValueChange,
        singleLine = true,
        label = {
            Text(
                text = labelText,
                color = DarkBlue
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                color = DarkBlue
            )
        },
        modifier = modifier
            .background(Cream, RoundedCornerShape(15.dp))
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 15.dp),
        leadingIcon = {
            Image(
                painter = leadingIconSrc,
                contentDescription = null
            )
        },
        keyboardOptions = keyboardType,
        keyboardActions = onKeyboardNext,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkBlue,
            unfocusedBorderColor = DarkBlue,
            cursorColor = DarkBlue
        )
            
    )
}

@Composable
fun PasswordOutlinedTextField(
    passwordInput: String,
    onPasswordInputValueChange: (String) -> Unit,
    passwordVisibilityIcon: Painter,
    labelText: String,
    placeholderText: String,
    onTrailingIconClick: () -> Unit,
    passwordVisibility: VisualTransformation,
    modifier: Modifier = Modifier,
    onKeyboardNext: KeyboardActions,
    keyboardImeAction: ImeAction
) {
    OutlinedTextField(
        value = passwordInput,
        onValueChange = onPasswordInputValueChange,
        singleLine = true,
        label = {
            Text(
                text = labelText,
                color = DarkBlue
            )
        },
        trailingIcon = {
            Image(
                painter = passwordVisibilityIcon,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onTrailingIconClick()
                    }
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                color = DarkBlue
            )
        },
        visualTransformation = passwordVisibility,
        modifier = modifier
            .background(Cream, RoundedCornerShape(15.dp))
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 15.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = keyboardImeAction
        ),
        keyboardActions = onKeyboardNext,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkBlue,
            unfocusedBorderColor = DarkBlue,
            cursorColor = DarkBlue
        )
    )
}

@Composable
fun AuthenticationButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    buttonModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    buttonColor: Color,
    userDataStatusUIState: AuthenticationStatusUIState,
    loadingBarModifier: Modifier = Modifier
) {
    when(userDataStatusUIState) {
        is AuthenticationStatusUIState.Loading -> CircleLoadingTemplate(
            modifier = loadingBarModifier,
            color = DarkBlue,
            trackColor = Color.Transparent
        )
        else -> Button(
            onClick = onButtonClick,
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(buttonColor),
            enabled = buttonEnabled
        ) {
            Text(
                text = buttonText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = textModifier,
                color = DarkBlue
            )
        }
    }

}

@Composable
fun AuthenticationQuestion(
    questionText: String,
    actionText: String,
    rowModifier: Modifier = Modifier,
    onActionTextClicked: () -> Unit,
) {
    Row(
        modifier = rowModifier
    ) {
        Text(
            text = questionText,
            color = DarkBlue
        )

        Text(
            text = actionText,
            color = Cream,
            modifier = Modifier
                .clickable {
                    onActionTextClicked()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationTemplatePreview() {
    StarchiveTheme {
        Column(
            modifier = Modifier
                
                .fillMaxSize()

                .padding(16.dp)
                ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthenticationOutlinedTextField(
                inputValue = "Username",
                onInputValueChange = {},
                labelText = "Username",
                placeholderText = "Enter your username",
                leadingIconSrc = painterResource(id = R.drawable.ic_username),
                keyboardType = KeyboardOptions.Default,
                onKeyboardNext = KeyboardActions.Default
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordOutlinedTextField(
                passwordInput = "Password",
                onPasswordInputValueChange = {},
                passwordVisibilityIcon = painterResource(id = R.drawable.ic_password_visible),
                labelText = "Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = {},
                passwordVisibility = VisualTransformation.None,
                onKeyboardNext = KeyboardActions.Default,
                keyboardImeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(16.dp))
            AuthenticationButton(
                buttonText = "Login",
                onButtonClick = {},
                buttonEnabled = true,
                buttonColor = Cream,
                userDataStatusUIState = AuthenticationStatusUIState.Loading,
                loadingBarModifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            AuthenticationQuestion(
                questionText = "Don't have an account?",
                actionText = "Sign Up",
                onActionTextClicked = {},
                rowModifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}