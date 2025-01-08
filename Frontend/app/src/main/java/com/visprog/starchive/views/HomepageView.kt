package com.visprog.starchive.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.visprog.starchive.ui.theme.StarchiveTheme
import com.visprog.starchive.uiStates.HomepageDataStatusUIState
import com.visprog.starchive.viewmodels.HomepageViewModel
import com.visprog.starchive.views.templates.CommonTemplate

@Composable
fun HomepageView(
    homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory),
    token: String,
    gameId: Int
) {
    val dataStatus by homepageViewModel.dataStatus.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homepageViewModel.getBudgets(token, gameId)
    }

    CommonTemplate(currentScreen = "HOMEPAGE", onNavigate = {}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            when (dataStatus) {
                is HomepageDataStatusUIState.Success -> {
                    val budget = (dataStatus as HomepageDataStatusUIState.Success).data
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(161.dp)
                            .border(
                                width = 4.dp,
                                color = Color(0xFFFFA500), // Gold border
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF9F5E9), // Light cream background
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .fillMaxWidth()
                                .requiredHeight(161.dp),
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
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Rp ${budget.remainingBudget}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = "STELLAR JADES",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${budget.remainingCurrency}",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "STAR RAIL PASSES",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${budget.remainingTickets}",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(y = (-176).dp, x = (-64).dp)
                            .zIndex(1f)
                            .background(
                                color = Color(0xFFFFA500),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AVAILABLE BUDGET",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                is HomepageDataStatusUIState.Failed -> {
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
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun HomepagePreview() {
    StarchiveTheme(dynamicColor = false) {
        HomepageView(token = "sample_token", gameId = 1)
    }
}