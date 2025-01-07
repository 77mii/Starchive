package com.visprog.starchive

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.visprog.starchive.routes.SetupNavGraph
import com.visprog.starchive.ui.theme.StarchiveTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            StarchiveApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StarchiveApp() {
    StarchiveTheme(dynamicColor = false) {
        SetupNavGraph()
    }
}