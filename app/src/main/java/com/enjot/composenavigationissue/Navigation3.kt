package com.enjot.composenavigationissue

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

// Implementation based on
// https://github.com/android/nav3-recipes/blob/main/app/src/main/java/com/example/nav3recipes/commonui/CommonUiActivity.kt


private interface TopLevelDestination {
    val name: String
    val icon: ImageVector
}

data object Home : TopLevelDestination {
    override val name = "Home"
    override val icon = Icons.Default.Home
}

data object Map : TopLevelDestination {
    override val name = "Map"
    override val icon = Icons.Default.Map
}

data object Detail

private val TOP_LEVEL_ROUTES: List<TopLevelDestination> = listOf(Home, Map)

@Composable
fun Navigation3() {
    val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                    val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                    NavBarItem(
                        name = topLevelRoute.name,
                        icon = topLevelRoute.icon,
                        isSelected = isSelected,
                        onClick = {
                            topLevelBackStack.addTopLevel(topLevelRoute)
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen(
                        navigateToDetailScreen = {
                            topLevelBackStack.add(Detail)
                        }
                    )
                }
                entry<Map> {
                    MapScreen(
                        navigateToDetailScreen = { topLevelBackStack.add(Detail) }
                    )
                }
                entry<Detail> {
                    DetailScreen()
                }
            },
            modifier = Modifier.padding(contentPadding)
        )
    }
}

class TopLevelBackStack<T : Any>(startKey: T) {

    // Maintain a stack for each top level route
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {

        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        if (topLevelStacks[topLevelKey]!!.contains(key))
            return

        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}