package com.enjot.composenavigationissue

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

// Implementation based on
// https://developer.android.com/develop/ui/compose/navigation#bottom-nav


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    val selected = currentDestination?.hierarchy
                        ?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    NavBarItem(
                        name = topLevelRoute.name,
                        icon = topLevelRoute.icon,
                        isSelected = selected,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    navigateToDetailScreen = { navController.navigate(DetailRoute) }
                )
            }
            composable<MapRoute> {
                LifecycleOwner(
                    maxLifecycle = Lifecycle.State.RESUMED,
                    parentLifecycleOwner = lifecycleOwner
                ) {
                    MapScreen(
                        navigateToDetailScreen = { navController.navigate(DetailRoute) }
                    )
                }
            }
            composable<DetailRoute> {
                DetailScreen()
            }
        }
    }
}

@Composable
fun RowScope.NavBarItem(
    name: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = name
            )
        },
        label = { Text(name) },
        selected = isSelected,
        onClick = onClick
    )
}