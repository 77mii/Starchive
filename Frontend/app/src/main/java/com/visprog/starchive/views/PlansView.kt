package com.visprog.starchive.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.PlanModel
import com.visprog.starchive.models.PlanRequest
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import com.visprog.starchive.uiStates.PlansDataStatusUIState
import com.visprog.starchive.viewmodels.BudgetingViewModel
import com.visprog.starchive.viewmodels.PlanViewModel
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun PlansView(
    planViewModel: PlanViewModel = viewModel(factory = PlanViewModel.Factory),
    budgetingViewModel: BudgetingViewModel = viewModel(factory = BudgetingViewModel.Factory),
    token: String,
    gameId: Int,
    navController: NavHostController
) {
    val plansDataStatus by planViewModel.plansDataStatus.collectAsStateWithLifecycle()
    val budgetDataStatus by budgetingViewModel.dataStatus.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    var planDescription by remember { mutableStateOf("") }
    var planBudget by remember { mutableStateOf("") }
    var planCurrency by remember { mutableStateOf("") }
    var planTickets by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { 
        planViewModel.getPlans(token)
        budgetingViewModel.getBudgets(token, gameId)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = MaterialTheme.colorScheme.secondary,
            title = {
                Text(
                    text = "Add Plan",
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
                        value = planDescription,
                        onValueChange = { planDescription = it },
                        label = { 
                            Text(
                                "Plan Description",
                                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                            ) 
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = planBudget,
                        onValueChange = { planBudget = it },
                        label = { 
                            Text(
                                "Budget Amount",
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
                        value = planCurrency,
                        onValueChange = { planCurrency = it },
                        label = { 
                            Text(
                                "Currency Amount",
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
                        value = planTickets,
                        onValueChange = { planTickets = it },
                        label = { 
                            Text(
                                "Tickets Amount",
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
            when (budgetDataStatus) {
                is BudgetDataStatusUIState.Success -> {
                    val currentBudget = (budgetDataStatus as BudgetDataStatusUIState.Success).data.data
                    val plannedBudget = planBudget.toFloatOrNull() ?: 0f
                    val plannedCurrency = planCurrency.toFloatOrNull() ?: 0f
                    val plannedTickets = planTickets.toFloatOrNull() ?: 0f

                    if (plannedBudget > currentBudget.remaining_budget ||
                    plannedCurrency > currentBudget.remaining_currency ||
                    plannedTickets > currentBudget.remaining_tickets) {
                    return@Button
                    }

                    // Create plan
                    val plan = PlanRequest(
                        gameId = gameId,
                        planDescription = planDescription,
                        planBudget = planBudget.toFloatOrNull() ?: 0f,
                        planCurrency = planCurrency.toFloatOrNull() ?: 0f,
                        planTickets = planTickets.toFloatOrNull() ?: 0f
                    )
                    
                    // Update budget with decreased remaining values
                    val updatedBudget = BudgetModel(
                        budgetId = currentBudget.budgetId,
                        gameId = currentBudget.gameId,
                        allocated_budget = currentBudget.allocated_budget,
                        allocated_currency = currentBudget.allocated_currency,
                        allocated_tickets = currentBudget.allocated_tickets,
                        remaining_budget = (currentBudget.remaining_budget - plan.planBudget).coerceAtLeast(0f),
                        remaining_currency = (currentBudget.remaining_currency - plan.planCurrency).coerceAtLeast(0f),
                        remaining_tickets = (currentBudget.remaining_tickets - plan.planTickets).coerceAtLeast(0f)
                    )

                    // Create plan first
                    planViewModel.createPlan(token, plan)
                    
                    // Then update budget
                    budgetingViewModel.updateBudget(token, currentBudget.budgetId, updatedBudget)

                    showDialog = false
                    planDescription = ""
                    planBudget = ""
                    planCurrency = ""
                    planTickets = ""
                }
                else -> {
                    Log.e("PlansView", "Budget data not available")
                }
            }
        },
                    enabled = planDescription.isNotEmpty() && 
                             (planBudget.isNotEmpty() || 
                              planCurrency.isNotEmpty() || 
                              planTickets.isNotEmpty()),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Add Plan",
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

    CommonTemplate(
            currentScreen = "Plans",
            gameId = gameId,
            onNavigate = { screen, gameId ->
                when (screen) {
                    "Homepage" -> navController.navigate("${PagesEnum.Homepage.name}/$gameId")
                    "Budgeting" -> navController.navigate("${PagesEnum.Budgeting.name}/$gameId")
                    "Pullsim" -> navController.navigate("${PagesEnum.Pullsim.name}/$gameId")
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
            Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors =
                            ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                            ),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                        text = "Add Plan",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            when (plansDataStatus) {
                is PlansDataStatusUIState.Success -> {
                    val plans = (plansDataStatus as PlansDataStatusUIState.Success).data.data
                    LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(plans) { plan ->
                            Box(
                                    modifier =
                                            Modifier.aspectRatio(1f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(MaterialTheme.colorScheme.secondary)
                                                    .clickable {
                                                    }
                            ) {
                                Column(
                                        modifier = Modifier.fillMaxSize().padding(16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                            text = plan.planDescription,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily =
                                                    MaterialTheme.typography.bodyLarge.fontFamily,
                                            color = MaterialTheme.colorScheme.primary
                                    )
                                        Text(
                                                text = "Budget: ${plan.planBudget.toInt()}",
                                                fontSize = 16.sp,
                                                fontFamily =
                                                        MaterialTheme.typography.bodyMedium.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                                text = "Currency: ${plan.planCurrency.toInt()}",
                                                fontSize = 16.sp,
                                                fontFamily =
                                                        MaterialTheme.typography.bodyMedium.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                                text = "Tickets: ${plan.planTickets.toInt()}",
                                                fontSize = 16.sp,
                                                fontFamily =
                                                        MaterialTheme.typography.bodyMedium.fontFamily,
                                                color = MaterialTheme.colorScheme.primary
                                        )
                                }
                            }
                        }
                    }
                }
                is PlansDataStatusUIState.Failed -> {
                    Text(
                            text = "Failed to load plans",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is PlansDataStatusUIState.Loading -> {
                    CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
