package com.github.wlara.nextgen.ui.home

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
import com.github.wlara.nextgen.AppNavigation
import com.github.wlara.nextgen.R
import com.github.wlara.nextgen.NavNode
import com.github.wlara.nextgen.AllNavNodes
import kotlinx.coroutines.flow.collect

@Composable
internal fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            val currentRootNode by navController.currentRootNodeAsState()
            HomeBottomNavigation(
                currentRootNode = currentRootNode,
                onNavigationSelected = { navController.navigate(it.route) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AppNavigation(navController)
        }
    }
}

@Composable
internal fun HomeBottomNavigation(
    currentRootNode: NavNode,
    onNavigationSelected: (NavNode) -> Unit
) {
    BottomNavigation {
        HomeBottomNavigationItem(
            icon = Icons.Filled.Forum,
            selected = currentRootNode == NavNode.PostRoot,
            label = stringResource(R.string.label_posts),
            onClick = { onNavigationSelected(NavNode.PostRoot) }
        )
        HomeBottomNavigationItem(
            icon = Icons.Filled.Comment,
            selected = currentRootNode == NavNode.CommentRoot,
            label = stringResource(R.string.label_comments),
            onClick = { onNavigationSelected(NavNode.CommentRoot) }
        )
        HomeBottomNavigationItem(
            icon = Icons.Filled.People,
            selected = currentRootNode == NavNode.UserRoot,
            label = stringResource(R.string.label_users),
            onClick = { onNavigationSelected(NavNode.UserRoot) }
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
private fun NavController.currentRootNodeAsState(): State<NavNode> {
    val currentRootNode = remember { mutableStateOf<NavNode>(NavNode.PostRoot) }
    LaunchedEffect(this) {
        currentBackStackEntryFlow.collect { entry ->
            AllNavNodes.find { entry.destination.parent?.route == it.route }?.let {
                currentRootNode.value = it
            }
        }
    }
    return currentRootNode
}