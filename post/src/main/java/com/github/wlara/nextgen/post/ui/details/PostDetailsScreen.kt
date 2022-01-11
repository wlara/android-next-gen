package com.github.wlara.nextgen.post.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.github.wlara.nextgen.core.components.FlatTopBar
import com.github.wlara.nextgen.core.components.RoundedButton
import com.github.wlara.nextgen.post.R

@Composable
fun PostDetailsScreen(
    postId: Int,
    onShowUserDetails: (Int) -> Unit,
    onShowPostComments: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FlatTopBar(
                title = stringResource(R.string.title_post_details),
                onClick = onNavigateUp
            )
        }

    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.refresh() },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 5.dp,
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.post?.title.orEmpty(),
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onSurface,
                        )
                        Text(
                            text = uiState.post?.body.orEmpty(),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                RoundedButton(
                    text = stringResource(R.string.button_author),
                    onClick = { onShowUserDetails(uiState.post?.userId ?: 0) },
                    modifier = Modifier.fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(20.dp))
                RoundedButton(
                    text = stringResource(R.string.button_comments),
                    onClick = { onShowPostComments(postId) },
                    modifier = Modifier.fillMaxWidth()
                )
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
