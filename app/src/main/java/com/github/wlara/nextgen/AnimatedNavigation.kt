package com.github.wlara.nextgen
/*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.github.wlara.nextgen.ui.home.HomeScreen
import com.github.wlara.nextgen.ui.profile.ProfileScreen


// Reference:
// https://google.github.io/accompanist/navigation-animation/
// https://medium.com/androiddevelopers/animations-in-navigation-compose-36d48870776b


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavigation(navController: NavHostController = rememberAnimatedNavController()) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeScreen(navController)
        addProfileScreen(navController)
        addFeedLandingScreen(navController)
        addFeedDetailsScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addHomeScreen(navController: NavHostController) {
    composable(
        route = Screen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(2000)) }
    ) {
        HomeScreen(
            showProfileScreen = { navController.navigate(Screen.Profile.route) },
            showFeedLandingScreen = { navController.navigate(Screen.FeedLanding.route) }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addProfileScreen(navController: NavHostController) {
    composable(Screen.Profile.route) {
        ProfileScreen(
            navigateUp = { navController.popBackStack() }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addFeedLandingScreen(navController: NavHostController) {
    composable(Screen.FeedLanding.route) {
        FeedLandingScreen(
            navigateUp = { navController.popBackStack() },
            showFeedDetailsScreen = { navController.navigate(Screen.FeedDetails.routeOf(it)) }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addFeedDetailsScreen(navController: NavHostController) {
    composable(
        route = Screen.FeedDetails.route,
        arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        ),
        enterTransition = { slideInHorizontally() }
    ) {
        FeedDetailsScreen(
            id = requireNotNull(it.arguments?.getString("id")),
            navigateUp = { navController.popBackStack() }
        )
    }
}
*/
