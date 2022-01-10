package com.github.wlara.nextgen.nextgen

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.github.wlara.nextgen.nextgen.comment.ui.details.CommentDetailsScreen
import com.github.wlara.nextgen.nextgen.comment.ui.landing.CommentLandingScreen
import com.github.wlara.nextgen.nextgen.comment.ui.post.PostCommentsScreen
import com.github.wlara.nextgen.nextgen.core.ext.encodeUrl
import com.github.wlara.nextgen.nextgen.core.navigation.ARG_COMMENT_ID
import com.github.wlara.nextgen.nextgen.core.navigation.ARG_POST_ID
import com.github.wlara.nextgen.nextgen.core.navigation.ARG_USER_ID
import com.github.wlara.nextgen.nextgen.core.navigation.DEEPLINK_COMMENT_ID
import com.github.wlara.nextgen.nextgen.core.navigation.DEEPLINK_POST_ID
import com.github.wlara.nextgen.nextgen.core.navigation.DEEPLINK_USER_ID
import com.github.wlara.nextgen.nextgen.core.navigation.URL_RAW_COMMENTS
import com.github.wlara.nextgen.nextgen.core.navigation.URL_RAW_POSTS
import com.github.wlara.nextgen.nextgen.core.navigation.URL_RAW_USERS
import com.github.wlara.nextgen.nextgen.post.ui.details.PostDetailsScreen
import com.github.wlara.nextgen.nextgen.post.ui.landing.PostLandingScreen
import com.github.wlara.nextgen.nextgen.post.ui.user.UserPostsScreen
import com.github.wlara.nextgen.nextgen.user.ui.details.UserDetailsScreen
import com.github.wlara.nextgen.nextgen.user.ui.landing.UserLandingScreen
import com.github.wlara.nextgen.nextgen.webviewer.WebViewerActivity

// Inspired by Chris Bane's Tivi app:
// https://medium.com/google-developer-experts/navigating-in-jetpack-compose-78c78d365c6a
// https://github.com/chrisbanes/tivi/blob/3de631a945d371326f7b817a3bfadcdae55275a2/app/src/main/java/app/tivi/AppNavigation.kt

sealed class Screen(
    val route: String
) {
    object PostRoot : Screen("post-root")

    object PostLanding : Screen("post-landing")

    object PostDetails : Screen("post-details/{$ARG_POST_ID}") {
        fun routeOf(postId: Int) = "post-details/$postId"
    }

    object UserPosts : Screen("user-posts/{$ARG_USER_ID}") {
        fun routeOf(userId: Int) = "user-posts/$userId"
    }

    object CommentRoot : Screen("comment-root")

    object CommentLanding : Screen("comment-landing")

    object CommentDetails : Screen("comment-details/{$ARG_COMMENT_ID}") {
        fun routeOf(commentId: Int) = "comment-details/$commentId"
    }

    object PostComments : Screen("post-comments/{$ARG_POST_ID}") {
        fun routeOf(postId: Int) = "post-comments/$postId"
    }

    object UserRoot : Screen("user-root")

    object UserLanding : Screen("user-landing")

    object UserDetails : Screen("user-details/{$ARG_USER_ID}") {
        fun routeOf(userId: Int) = "user-details/$userId"
    }

    object WebViewer : Screen(
        "web-viewer/{${WebViewerActivity.EXTRA_URL}}"
    ) {
        fun routeOf(url: String) = "web-viewer/${url.encodeUrl()}"
    }
}

val allScreens: List<Screen>
    get() = Screen::class.sealedSubclasses.map { it.objectInstance as Screen }

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PostRoot.route
    ) {
        addPostTree(navController)
        addCommentTree(navController)
        addUserTree(navController)
        addWebViewerScreen()
    }
}

// region Post

private fun NavGraphBuilder.addPostTree(navController: NavHostController) {
    navigation(
        route = Screen.PostRoot.route,
        startDestination = Screen.PostLanding.route
    ) {
        addPostLandingScreen(navController)
        addPostDetailsScreen(navController)
        addPostCommentsScreen(navController)
    }
}

private fun NavGraphBuilder.addPostLandingScreen(navController: NavHostController) {
    composable(Screen.PostLanding.route) {
        PostLandingScreen(
            onShowPostDetails = { navController.navigate(Screen.PostDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(Screen.WebViewer.routeOf(URL_RAW_POSTS)) }
        )
    }
}

private fun NavGraphBuilder.addPostDetailsScreen(navController: NavHostController) {
    composable(
        route = Screen.PostDetails.route,
        arguments = listOf(
            navArgument(ARG_POST_ID) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEPLINK_POST_ID
            }
        )
    ) { entry ->
        PostDetailsScreen(
            postId = entry.arguments?.getInt(ARG_POST_ID)!!,
            onShowUserDetails = { navController.navigate(Screen.UserDetails.routeOf(it)) },
            onShowPostComments = { navController.navigate(Screen.PostComments.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addUserPostsScreen(navController: NavHostController) {
    composable(
        route = Screen.UserPosts.route,
        arguments = listOf(
            navArgument(ARG_USER_ID) { type = NavType.IntType }
        )
    ) { entry ->
        UserPostsScreen(
            userId = entry.arguments?.getInt(ARG_USER_ID)!!,
            onShowPostDetails = { navController.navigate(Screen.PostDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region Comment

private fun NavGraphBuilder.addCommentTree(navController: NavHostController) {
    navigation(
        route = Screen.CommentRoot.route,
        startDestination = Screen.CommentLanding.route
    ) {
        addCommentLandingScreen(navController)
        addCommentDetailsScreen(navController)
    }
}

private fun NavGraphBuilder.addCommentLandingScreen(navController: NavHostController) {
    composable(Screen.CommentLanding.route) {
        CommentLandingScreen(
            onShowCommentDetails = { navController.navigate(Screen.CommentDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(Screen.WebViewer.routeOf(URL_RAW_COMMENTS)) }
        )
    }
}

private fun NavGraphBuilder.addCommentDetailsScreen(navController: NavHostController) {
    composable(
        route = Screen.CommentDetails.route,
        arguments = listOf(
            navArgument(ARG_COMMENT_ID) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEPLINK_COMMENT_ID
            }
        )
    ) { entry ->
        CommentDetailsScreen(
            commentId = entry.arguments?.getInt(ARG_COMMENT_ID)!!,
            onShowPostDetails = { navController.navigate(Screen.PostDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addPostCommentsScreen(navController: NavHostController) {
    composable(
        route = Screen.PostComments.route,
        arguments = listOf(
            navArgument(ARG_POST_ID) { type = NavType.IntType }
        )
    ) { entry ->
        PostCommentsScreen(
            postId = entry.arguments?.getInt(ARG_POST_ID)!!,
            onShowCommentDetails = { navController.navigate(Screen.CommentDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region User

private fun NavGraphBuilder.addUserTree(navController: NavHostController) {
    navigation(
        route = Screen.UserRoot.route,
        startDestination = Screen.UserLanding.route
    ) {
        addUserLandingScreen(navController)
        addUserDetailsScreen(navController)
        addUserPostsScreen(navController)
    }
}

private fun NavGraphBuilder.addUserLandingScreen(navController: NavHostController) {
    composable(Screen.UserLanding.route) {
        UserLandingScreen(
            onShowUserDetails = { navController.navigate(Screen.UserDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(Screen.WebViewer.routeOf(URL_RAW_USERS)) }
        )
    }
}

private fun NavGraphBuilder.addUserDetailsScreen(navController: NavHostController) {
    composable(
        route = Screen.UserDetails.route,
        arguments = listOf(
            navArgument(ARG_USER_ID) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEPLINK_USER_ID
            }
        )
    ) { entry ->
        UserDetailsScreen(
            userId = entry.arguments?.getInt(ARG_USER_ID)!!,
            onShowUserPosts = { navController.navigate(Screen.UserPosts.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region Settings

private fun NavGraphBuilder.addWebViewerScreen() {
    activity(
        route = Screen.WebViewer.route
    ) {
        activityClass = WebViewerActivity::class
        argument(WebViewerActivity.EXTRA_URL) { type = NavType.StringType }
    }
}

// endregion