package com.visprog.starchive.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import com.visprog.starchive.uiStates.GameDataStatusUIState
import com.visprog.starchive.viewmodels.BudgetingViewModel
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun BudgetingView(
    budgetingViewModel: BudgetingViewModel = viewModel(factory = BudgetingViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController
) {
    val dataStatus by budgetingViewModel.dataStatus.collectAsStateWithLifecycle()
    val gameData by budgetingViewModel.gameData.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var isDeposit by remember { mutableStateOf(false) }

    var budgetAmount by remember { mutableStateOf("") }
    var currencyAmount by remember { mutableStateOf("") }
    var ticketsAmount by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        budgetingViewModel.getBudgets(token, gameId)
        budgetingViewModel.getGame(token, gameId)
    }

    if (showDialog) {
        when (gameData) {
            is GameDataStatusUIState.Success -> {
                val game = (gameData as GameDataStatusUIState.Success).data
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    title = {
                        Text(
                            text = if (isDeposit) "Deposit" else "Withdraw",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            OutlinedTextField(
                                value = budgetAmount,
                                onValueChange = { budgetAmount = it },
                                label = {
                                    Text(
                                        "Real Cash Amount",
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.primary
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            OutlinedTextField(
                                value = currencyAmount,
                                onValueChange = { currencyAmount = it },
                                label = {
                                    Text(
                                        "${game.data.currencyName ?: "Currency"} Amount",
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.primary
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            OutlinedTextField(
                                value = ticketsAmount,
                                onValueChange = { ticketsAmount = it },
                                label = {
                                    Text(
                                        "${game.data.ticketsName ?: "Tickets"} Amount",
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.primary
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                when (dataStatus) {
                                    is BudgetDataStatusUIState.Success -> {
                                        val currentBudget =
                                            (dataStatus as BudgetDataStatusUIState.Success).data.data

                                        // Refresh budget data first
                                        budgetingViewModel.getBudgets(token, gameId)

                                        // Then update with new values
                                        val budgetValue = budgetAmount.toFloatOrNull() ?: 0f
                                        val currencyValue = currencyAmount.toFloatOrNull() ?: 0f
                                        val ticketsValue = ticketsAmount.toFloatOrNull() ?: 0f

                                        if (currentBudget.budgetId > 0) {
                                            // Validation for withdrawal
                                            if (!isDeposit) {
                                                if (budgetValue > currentBudget.remaining_budget ||
                                                    currencyValue > currentBudget.remaining_currency ||
                                                    ticketsValue > currentBudget.remaining_tickets
                                                ) {
                                                    // Cannot withdraw more than remaining amounts
                                                    return@Button
                                                }
                                            }

                                            val updatedBudget = BudgetModel(
                                                budgetId = currentBudget.budgetId,
                                                gameId = currentBudget.gameId,
                                                allocated_budget = if (isDeposit)
                                                    currentBudget.allocated_budget + budgetValue
                                                else
                                                    (currentBudget.allocated_budget - budgetValue).coerceAtLeast(
                                                        0f
                                                    ),
                                                allocated_currency = if (isDeposit)
                                                    currentBudget.allocated_currency + currencyValue
                                                else
                                                    (currentBudget.allocated_currency - currencyValue).coerceAtLeast(
                                                        0f
                                                    ),
                                                allocated_tickets = if (isDeposit)
                                                    currentBudget.allocated_tickets + ticketsValue
                                                else
                                                    (currentBudget.allocated_tickets - ticketsValue).coerceAtLeast(
                                                        0f
                                                    ),
                                                remaining_budget = if (isDeposit)
                                                    currentBudget.remaining_budget + budgetValue
                                                else
                                                    (currentBudget.remaining_budget - budgetValue).coerceAtLeast(
                                                        0f
                                                    ),
                                                remaining_currency = if (isDeposit)
                                                    currentBudget.remaining_currency + currencyValue
                                                else
                                                    (currentBudget.remaining_currency - currencyValue).coerceAtLeast(
                                                        0f
                                                    ),
                                                remaining_tickets = if (isDeposit)
                                                    currentBudget.remaining_tickets + ticketsValue
                                                else
                                                    (currentBudget.remaining_tickets - ticketsValue).coerceAtLeast(
                                                        0f
                                                    )
                                            )

                                            budgetingViewModel.updateBudget(
                                                token,
                                                updatedBudget.budgetId,
                                                updatedBudget
                                            )

                                            showDialog = false
                                            budgetAmount = ""
                                            currencyAmount = ""
                                            ticketsAmount = ""
                                        } else {
                                            Log.e(
                                                "BudgetingView",
                                                "Invalid budget ID: ${currentBudget.budgetId}"
                                            )
                                        }
                                    }

                                    else -> {
                                        Log.e("BudgetingView", "Failed to update budget data")
                                    }
                                }
                            },
                            enabled = budgetAmount.isNotEmpty() ||
                                    currencyAmount.isNotEmpty() ||
                                    ticketsAmount.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.secondary,
                                disabledContainerColor = Color.LightGray,
                                disabledContentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (isDeposit) "Deposit" else "Withdraw",
                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                )
            }

            else -> {
                Log.e("BudgetingView", "Failed to load game data")
            }
        }
    }

    CommonTemplate(
        currentScreen = "Budgeting",
        gameId = gameId,
        onNavigate = { screen, gameId ->
            when (screen) {
                "Homepage" -> navController.navigate("${PagesEnum.Homepage.name}/$gameId")
                "Budgeting" -> navController.navigate("${PagesEnum.Budgeting.name}/$gameId")
                "Pullsim" -> navController.navigate("${PagesEnum.Pullsim.name}/$gameId")
            }
        }
    ) {
        when (gameData) {
            is GameDataStatusUIState.Success -> {
                val game = (gameData as GameDataStatusUIState.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                ) {
                    when (dataStatus) {
                        is BudgetDataStatusUIState.Success -> {
                            val budget = (dataStatus as BudgetDataStatusUIState.Success).data
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Card(
                                    modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(265.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 4.dp,
                                            color = MaterialTheme.colorScheme.tertiary,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    Column(
                                        modifier =
                                        Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.secondary,
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .padding(vertical = 16.dp, horizontal = 16.dp)
                                            .fillMaxWidth()
                                            .requiredHeight(265.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(24.dp))

                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "REAL CASH",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text =
                                                "Rp ${budget.data.remaining_budget.toInt()} / ${budget.data.allocated_budget.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            LinearProgressIndicator(
                                                progress = {
                                                    budget.data.remaining_budget /
                                                            budget.data.allocated_budget
                                                },
                                                modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                color = MaterialTheme.colorScheme.primary,
                                                trackColor = Color.LightGray,
                                                strokeCap = StrokeCap.Round
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = game.data.currencyName?.uppercase()
                                                    ?: "Currency",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text =
                                                "${budget.data.remaining_currency.toInt()} / ${budget.data.allocated_currency.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            LinearProgressIndicator(
                                                progress = {
                                                    budget.data.remaining_currency /
                                                            budget.data.allocated_currency
                                                },
                                                modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                color = MaterialTheme.colorScheme.primary,
                                                trackColor = Color.LightGray,
                                                strokeCap = StrokeCap.Round
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = game.data.ticketsName?.uppercase()
                                                    ?: "Tickets",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text =
                                                "${budget.data.remaining_tickets.toInt()} / ${budget.data.allocated_tickets.toInt()}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily =
                                                MaterialTheme.typography.bodyLarge.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            LinearProgressIndicator(
                                                progress = {
                                                    budget.data.remaining_tickets /
                                                            budget.data.allocated_tickets
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                color = MaterialTheme.colorScheme.primary,
                                                trackColor = Color.LightGray,
                                                strokeCap = StrokeCap.Round
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Button(
                                    onClick = { isDeposit = false; showDialog = true },
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .requiredWidth(180.dp)
                                        .aspectRatio(1f),
                                    colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary
                                    )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = null,
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Text(
                                            text = "Withdraw",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Button(
                                    onClick = { isDeposit = true; showDialog = true },
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .requiredWidth(180.dp)
                                        .aspectRatio(1f),
                                    colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary
                                    )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowUp,
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = null,
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Text(
                                            text = "Deposit",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }

                        is BudgetDataStatusUIState.Failed -> {
                            Text(
                                text = "Failed to load budget data",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        else -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Button(
                            onClick = { navController.navigate("${PagesEnum.Plans.name}/$gameId") },
                            modifier = Modifier
                                .fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.MailOutline,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(48.dp)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "View Plans",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}