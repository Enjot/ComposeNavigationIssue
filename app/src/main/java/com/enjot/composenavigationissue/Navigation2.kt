package com.enjot.composenavigationissue

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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

@Serializable
private data object HomeRoute

@Serializable
private data object MapRoute

@Serializable
private data object DetailRoute

private data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

private val topLevelRoutes = listOf(
    TopLevelRoute("Home", HomeRoute, Icons.Default.Home),
    TopLevelRoute("Map", MapRoute, Icons.Default.Map)
)

@Composable
fun Navigation2() {
    val navController = rememberNavController()
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
                MapScreen(
                    navigateToDetailScreen = { navController.navigate(DetailRoute) }
                )
            }
            composable<DetailRoute> {
                DetailScreen()
            }
        }
    }
}