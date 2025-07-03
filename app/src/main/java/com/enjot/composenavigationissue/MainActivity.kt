package com.enjot.composenavigationissue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.enjot.composenavigationissue.ui.theme.ComposeNavigationIssueTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationIssueTheme {
//                Navigation2()
                Navigation3()
            }
        }
    }
}