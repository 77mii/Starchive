package com.visprog.starchive.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.visprog.starchive.R
import com.visprog.starchive.ui.theme.Cream
import com.visprog.starchive.ui.theme.DarkBlue
import com.visprog.starchive.ui.theme.StarchiveTheme

@Composable
fun CommonTemplateWithoutNavBar(
    currentScreen: String,
    gameId: Int,
    onNavigate: (String, Int) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        // Header content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(95.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image
            Box(
                modifier = Modifier
                    .requiredSize(60.dp)
                    .clip(CircleShape)
                    .background(color = Cream)
            ) {

            }

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_textlessdarkblue),
                contentDescription = "Logo",
                modifier = Modifier
                    .requiredSize(100.dp)
            )
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                content()
            }
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
fun CommonTemplateWithoutNavBarPreview() {
    StarchiveTheme(dynamicColor = false) {
        CommonTemplateWithoutNavBar(currentScreen = "Homepage", gameId = 1, onNavigate = { _, _ -> }) {
            Text("Content")
        }
    }
}
