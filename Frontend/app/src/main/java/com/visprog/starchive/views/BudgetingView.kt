


package com.visprog.starchive.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
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

    LaunchedEffect(Unit) { budgetingViewModel.getBudgets(token, gameId) }

    CommonTemplate(
        currentScreen = "Budgeting",
        gameId = gameId,
        onNavigate = { screen, gameId ->
            when (screen) {
                "Home" -> navController.navigate("${PagesEnum.Home.name}/$gameId")
                "Budgeting" -> navController.navigate("${PagesEnum.Budgeting.name}/$gameId")
                "Pullsim" -> navController.navigate("${PagesEnum.Pullsim.name}/$gameId")
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)) {
            when (dataStatus) {
                is BudgetDataStatusUIState.Success -> {
                    val budget = (dataStatus as BudgetDataStatusUIState.Success).data
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .requiredHeight(265.dp)
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
                                    // Added progress bar for Real Cash
                                    LinearProgressIndicator(
                                        progress = {
                                            budget.data.remaining_budget /
                                                    budget.data.allocated_budget
                                        },
                                        modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "STELLAR JADES",
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
                                    // Added progress bar for Stellar Jades
                                    LinearProgressIndicator(
                                        progress = {
                                            budget.data.remaining_currency /
                                                    budget.data.allocated_currency
                                        },
                                        modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "STAR RAIL PASSES",
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
                                    // Added progress bar for Star Rail Passes
                                    LinearProgressIndicator(
                                        progress = {
                                            budget.data.remaining_tickets /
                                                    budget.data.allocated_tickets
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
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
                            onClick = { /* TODO: Withdraw logic */ },
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
                            onClick = { /* TODO: Deposit logic */ },
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
                    onClick = { /* TODO: Change to View Plans view */ },
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