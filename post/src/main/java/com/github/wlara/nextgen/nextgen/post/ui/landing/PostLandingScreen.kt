package com.github.wlara.nextgen.nextgen.post.ui.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.github.wlara.nextgen.nextgen.post.R
import com.github.wlara.nextgen.nextgen.post.model.Post

@Composable
fun PostLandingScreen(
    onShowPostDetails: (Int) -> Unit,
    onShowRawData: () ->Unit,
    viewModel: PostLandingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.refresh() },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 25.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.header_posts),
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.clickable(onClick = onShowRawData)
                        )
                    }
                }
                items(uiState.posts) { PostItem(it) { onShowPostDetails(it.id) } }
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
private fun PostItem(post: Post, onClick: () -> Unit) {
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
                text = post.title,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}