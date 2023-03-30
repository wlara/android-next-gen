package com.github.wlara.nextgen.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.wlara.nextgen.AllNavNodes
import com.github.wlara.nextgen.AppNavigation
import com.github.wlara.nextgen.NavNode
import com.github.wlara.nextgen.R
import kotlinx.coroutines.flow.collect

private val PostsIconGradient = iconGradient(Color.Red, Color.White)
private val CommentsIconGradient = iconGradient(Color.Green, Color.White)
private val UsersIconGradient = iconGradient(Color.Magenta, Color.White)

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
            selected = currentRootNode == NavNode.PostRoot,
            iconResId = R.drawable.ic_posts,
            label = stringResource(R.string.label_posts),
            selectedIconBrush = PostsIconGradient,
            selectedLabelColor = Color.Red,
            onClick = { onNavigationSelected(NavNode.PostRoot) }
        )
        HomeBottomNavigationItem(
            selected = currentRootNode == NavNode.CommentRoot,
            iconResId = R.drawable.ic_comments,
            label = stringResource(R.string.label_comments),
            selectedIconBrush = CommentsIconGradient,
            selectedLabelColor = Color.Green,
            onClick = { onNavigationSelected(NavNode.CommentRoot) }
        )
        HomeBottomNavigationItem(
            selected = currentRootNode == NavNode.UserRoot,
            iconResId = R.drawable.ic_users,
            label = stringResource(R.string.label_users),
            selectedIconBrush = UsersIconGradient,
            selectedLabelColor = Color.Magenta,
            onClick = { onNavigationSelected(NavNode.UserRoot) }
        )
    }
}

@Composable
private fun RowScope.HomeBottomNavigationItem(
    selected: Boolean,
    iconResId: Int,
    label: String,
    selectedIconBrush: Brush,
    selectedLabelColor: Color,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = {
            Icon(
                modifier = if (selected) {
                    Modifier
                        .graphicsLayer(alpha = 0.99f)   // After migration to compose 1.4+ replace with .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(selectedIconBrush, blendMode = BlendMode.SrcAtop)
                            }
                        }
                } else {
                    Modifier
                },
                painter = painterResource(iconResId),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = label,
                color = if (selected) selectedLabelColor else Color.Unspecified
            )
        },
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

private fun iconGradient(
    startColor: Color,
    endColor: Color
) = Brush.linearGradient(
    colors = listOf(startColor, endColor),
    start = Offset(0f, Float.POSITIVE_INFINITY),    // bottom-left
    end = Offset(Float.POSITIVE_INFINITY, 0f)       // top-right
)

