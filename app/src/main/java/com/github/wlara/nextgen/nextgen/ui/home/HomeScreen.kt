package com.github.wlara.nextgen.nextgen.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.wlara.nextgen.nextgen.Navigation
import com.github.wlara.nextgen.nextgen.R
import com.github.wlara.nextgen.nextgen.Screen
import com.github.wlara.nextgen.nextgen.allScreens
import kotlinx.coroutines.flow.collect

@Composable
internal fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            val currentRootScreen by navController.currentRootScreenAsState()
            HomeBottomNavigation(
                currentRootScreen = currentRootScreen,
                onNavigationSelected = { navController.navigate(it.route) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController)
        }
    }
}

@Composable
internal fun HomeBottomNavigation(
    currentRootScreen: Screen,
    onNavigationSelected: (Screen) -> Unit
) {
    BottomNavigation {
        HomeBottomNavigationItem(
            icon = Icons.Filled.Forum,
            selected = currentRootScreen == Screen.PostRoot,
            label = stringResource(R.string.label_posts),
            onClick = { onNavigationSelected(Screen.PostRoot) }
        )
        HomeBottomNavigationItem(
            icon = Icons.Filled.Comment,
            selected = currentRootScreen == Screen.CommentRoot,
            label = stringResource(R.string.label_comments),
            onClick = { onNavigationSelected(Screen.CommentRoot) }
        )
        HomeBottomNavigationItem(
            icon = Icons.Filled.People,
            selected = currentRootScreen == Screen.UserRoot,
            label = stringResource(R.string.label_users),
            onClick = { onNavigationSelected(Screen.UserRoot) }
        )
    }
}

@Composable
private fun RowScope.HomeBottomNavigationItem(
    icon: ImageVector,
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}

@Composable
private fun NavController.currentRootScreenAsState(): State<Screen> {
    val currentRootScreen = remember { mutableStateOf<Screen>(Screen.PostRoot) }
    LaunchedEffect(this) {
        currentBackStackEntryFlow.collect { entry ->
            allScreens.find { entry.destination.parent?.route == it.route }?.let {
                currentRootScreen.value = it
            }
        }
    }
    return currentRootScreen
}