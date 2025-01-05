package com.example.signuplogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.visprog.starchive.R
import kotlin.collections.listOf as listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSelectionScreen() {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome Aboard",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Where are we headed this time?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val games = listOf(
            Pair("Honkai: Star Rail", R.drawable.ic_launcher_background),
            Pair("Arknights", R.drawable.ic_launcher_background)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            games.forEach { game ->
                GameCard(title = game.first, imageRes = game.second)
            }
        }
    }
}

@Composable
fun GameCard(title: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$title Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (title == "Honkai: Star Rail") "Turn-Based Space Fantasy RPG\nDeveloper: Hoyoverse"
            else "Tower Defense Strategy Game\nDeveloper: Yostar",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}