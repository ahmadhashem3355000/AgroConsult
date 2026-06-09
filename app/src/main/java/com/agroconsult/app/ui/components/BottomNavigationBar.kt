package com.agroconsult.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.agroconsult.app.ui.navigation.AppRoutes

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("الرئيسية") },
            selected = currentRoute == AppRoutes.HOME,
            onClick = {
                navController.navigate(AppRoutes.HOME) {
                    popUpTo(AppRoutes.HOME) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Shop, contentDescription = null) },
            label = { Text("السوق") },
            selected = currentRoute == AppRoutes.MARKETPLACE,
            onClick = {
                navController.navigate(AppRoutes.MARKETPLACE) {
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Message, contentDescription = null) },
            label = { Text("استشارات") },
            selected = currentRoute == AppRoutes.CONSULTATIONS,
            onClick = {
                navController.navigate(AppRoutes.CONSULTATIONS) {
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Mail, contentDescription = null) },
            label = { Text("رسائل") },
            selected = currentRoute == AppRoutes.MESSAGES,
            onClick = {
                navController.navigate(AppRoutes.MESSAGES) {
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("ملفي") },
            selected = currentRoute == AppRoutes.PROFILE,
            onClick = {
                navController.navigate(AppRoutes.PROFILE) {
                    launchSingleTop = true
                }
            }
        )
    }
}
