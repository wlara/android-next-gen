package com.github.wlara.nextgen.nextgen.comment.ui.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.github.wlara.nextgen.nextgen.comment.R
import com.github.wlara.nextgen.nextgen.core.components.FlatTopBar
import com.github.wlara.nextgen.nextgen.comment.model.Comment

@Composable
fun PostCommentsScreen(
    postId: Int,
    onShowCommentDetails: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: PostCommentsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FlatTopBar(
                title = stringResource(R.string.title_post_comments),
                onClick = onNavigateUp
            )
        }

    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.refresh() },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(uiState.comments) { CommentItem(it) { onShowCommentDetails(it.id) } }
            }
        }
    }

    uiState.errorMessage?.let { message ->
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(message = message)
            viewModel.clearError()
        }
    }
}

@Composable
private fun CommentItem(comment: Comment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = comment.name,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = comment.body,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}