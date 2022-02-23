package com.github.wlara.nextgen

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
import com.github.wlara.nextgen.comment.ui.details.CommentDetailsScreen
import com.github.wlara.nextgen.comment.ui.landing.CommentLandingScreen
import com.github.wlara.nextgen.comment.ui.post.PostCommentsScreen
import com.github.wlara.nextgen.core.ext.encodeUrl
import com.github.wlara.nextgen.core.navigation.ArgCommentId
import com.github.wlara.nextgen.core.navigation.ArgPostId
import com.github.wlara.nextgen.core.navigation.ArgUserId
import com.github.wlara.nextgen.core.navigation.DeeplinkCommentId
import com.github.wlara.nextgen.core.navigation.DeeplinkPostId
import com.github.wlara.nextgen.core.navigation.DeeplinkUserId
import com.github.wlara.nextgen.core.navigation.UrlRawComments
import com.github.wlara.nextgen.core.navigation.UrlRawPosts
import com.github.wlara.nextgen.core.navigation.UrlRawUsers
import com.github.wlara.nextgen.post.ui.details.PostDetailsScreen
import com.github.wlara.nextgen.post.ui.landing.PostLandingScreen
import com.github.wlara.nextgen.post.ui.user.UserPostsScreen
import com.github.wlara.nextgen.user.ui.details.UserDetailsScreen
import com.github.wlara.nextgen.user.ui.landing.UserLandingScreen
import com.github.wlara.nextgen.webviewer.WebViewerActivity

// Inspired by Chris Bane's Tivi app:
// https://medium.com/google-developer-experts/navigating-in-jetpack-compose-78c78d365c6a
// https://github.com/chrisbanes/tivi/blob/3de631a945d371326f7b817a3bfadcdae55275a2/app/src/main/java/app/tivi/AppNavigation.kt

sealed class NavNode(
    val route: String
) {
    object PostRoot : NavNode("post-root")

    object PostLanding : NavNode("post-landing")

    object PostDetails : NavNode("post-details/{$ArgPostId}") {
        fun routeOf(postId: Int) = "post-details/$postId"
    }

    object UserPosts : NavNode("user-posts/{$ArgUserId}") {
        fun routeOf(userId: Int) = "user-posts/$userId"
    }

    object CommentRoot : NavNode("comment-root")

    object CommentLanding : NavNode("comment-landing")

    object CommentDetails : NavNode("comment-details/{$ArgCommentId}") {
        fun routeOf(commentId: Int) = "comment-details/$commentId"
    }

    object PostComments : NavNode("post-comments/{$ArgPostId}") {
        fun routeOf(postId: Int) = "post-comments/$postId"
    }

    object UserRoot : NavNode("user-root")

    object UserLanding : NavNode("user-landing")

    object UserDetails : NavNode("user-details/{$ArgUserId}") {
        fun routeOf(userId: Int) = "user-details/$userId"
    }

    object WebViewer : NavNode(
        "web-viewer/{${WebViewerActivity.EXTRA_URL}}"
    ) {
        fun routeOf(url: String) = "web-viewer/${url.encodeUrl()}"
    }
}

val AllNavNodes: List<NavNode>
    get() = NavNode::class.sealedSubclasses.map { it.objectInstance as NavNode }

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavNode.PostRoot.route
    ) {
        addPostTree(navController)
        addCommentTree(navController)
        addUserTree(navController)
        addWebViewerNode()
    }
}

// region Post

private fun NavGraphBuilder.addPostTree(navController: NavHostController) {
    navigation(
        route = NavNode.PostRoot.route,
        startDestination = NavNode.PostLanding.route
    ) {
        addPostLandingNode(navController)
        addPostDetailsNode(navController)
        addPostCommentsNode(navController)
    }
}

private fun NavGraphBuilder.addPostLandingNode(navController: NavHostController) {
    composable(NavNode.PostLanding.route) {
        PostLandingScreen(
            onShowPostDetails = { navController.navigate(NavNode.PostDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(NavNode.WebViewer.routeOf(UrlRawPosts)) }
        )
    }
}

private fun NavGraphBuilder.addPostDetailsNode(navController: NavHostController) {
    composable(
        route = NavNode.PostDetails.route,
        arguments = listOf(
            navArgument(ArgPostId) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DeeplinkPostId
            }
        )
    ) { entry ->
        PostDetailsScreen(
            postId = entry.arguments?.getInt(ArgPostId)!!,
            onShowUserDetails = { navController.navigate(NavNode.UserDetails.routeOf(it)) },
            onShowPostComments = { navController.navigate(NavNode.PostComments.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addUserPostsNode(navController: NavHostController) {
    composable(
        route = NavNode.UserPosts.route,
        arguments = listOf(
            navArgument(ArgUserId) { type = NavType.IntType }
        )
    ) { entry ->
        UserPostsScreen(
            userId = entry.arguments?.getInt(ArgUserId)!!,
            onShowPostDetails = { navController.navigate(NavNode.PostDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region Comment

private fun NavGraphBuilder.addCommentTree(navController: NavHostController) {
    navigation(
        route = NavNode.CommentRoot.route,
        startDestination = NavNode.CommentLanding.route
    ) {
        addCommentLandingNode(navController)
        addCommentDetailsNode(navController)
    }
}

private fun NavGraphBuilder.addCommentLandingNode(navController: NavHostController) {
    composable(NavNode.CommentLanding.route) {
        CommentLandingScreen(
            onShowCommentDetails = { navController.navigate(NavNode.CommentDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(NavNode.WebViewer.routeOf(UrlRawComments)) }
        )
    }
}

private fun NavGraphBuilder.addCommentDetailsNode(navController: NavHostController) {
    composable(
        route = NavNode.CommentDetails.route,
        arguments = listOf(
            navArgument(ArgCommentId) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DeeplinkCommentId
            }
        )
    ) { entry ->
        CommentDetailsScreen(
            commentId = entry.arguments?.getInt(ArgCommentId)!!,
            onShowPostDetails = { navController.navigate(NavNode.PostDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addPostCommentsNode(navController: NavHostController) {
    composable(
        route = NavNode.PostComments.route,
        arguments = listOf(
            navArgument(ArgPostId) { type = NavType.IntType }
        )
    ) { entry ->
        PostCommentsScreen(
            postId = entry.arguments?.getInt(ArgPostId)!!,
            onShowCommentDetails = { navController.navigate(NavNode.CommentDetails.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region User

private fun NavGraphBuilder.addUserTree(navController: NavHostController) {
    navigation(
        route = NavNode.UserRoot.route,
        startDestination = NavNode.UserLanding.route
    ) {
        addUserLandingNode(navController)
        addUserDetailsNode(navController)
        addUserPostsNode(navController)
    }
}

private fun NavGraphBuilder.addUserLandingNode(navController: NavHostController) {
    composable(NavNode.UserLanding.route) {
        UserLandingScreen(
            onShowUserDetails = { navController.navigate(NavNode.UserDetails.routeOf(it)) },
            onShowRawData = { navController.navigate(NavNode.WebViewer.routeOf(UrlRawUsers)) }
        )
    }
}

private fun NavGraphBuilder.addUserDetailsNode(navController: NavHostController) {
    composable(
        route = NavNode.UserDetails.route,
        arguments = listOf(
            navArgument(ArgUserId) { type = NavType.IntType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DeeplinkUserId
            }
        )
    ) { entry ->
        UserDetailsScreen(
            userId = entry.arguments?.getInt(ArgUserId)!!,
            onShowUserPosts = { navController.navigate(NavNode.UserPosts.routeOf(it)) },
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

// endregion
// region Settings

private fun NavGraphBuilder.addWebViewerNode() {
    activity(
        route = NavNode.WebViewer.route
    ) {
        activityClass = WebViewerActivity::class
        argument(WebViewerActivity.EXTRA_URL) { type = NavType.StringType }
    }
}

// endregion