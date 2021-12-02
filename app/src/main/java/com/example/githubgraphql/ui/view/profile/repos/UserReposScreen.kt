package com.example.githubgraphql.ui.view.profile.repos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.githubgraphql.data.local.entity.RepositoryEntity
import com.example.githubgraphql.ui.components.Loading
import com.example.githubgraphql.ui.components.errorToast
import com.example.githubgraphql.ui.theme.Grey
import com.example.githubgraphql.ui.view.profile.ProfileViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun UserReposScreen(viewModel: ProfileViewModel) {
    val repos = viewModel.repos.collectAsLazyPagingItems()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
    ) {
        RepoList(repos = repos)
    }
}

@ExperimentalPagingApi
@ExperimentalMaterialApi
@Composable
fun RepoList(repos: LazyPagingItems<RepositoryEntity>) {
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val context = LocalContext.current

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000L)
            isRefreshing = false
        }
    }

    Box {
        repos.apply {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    repos.refresh()
                    isRefreshing = true
                },
                swipeEnabled = loadState.refresh !is LoadState.Loading
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(items = repos) { item -> RepoRow(repo = item) }
                    if (loadState.append is LoadState.Loading) {
                        item { Loading() }
                    } else if (loadState.append is LoadState.Error) {
                        errorToast(context)
                        item { Error(onRetryClick = { repos.retry() }) }
                    }
                }
            }
            if (loadState.refresh is LoadState.Loading) {
                Loading()
            } else if (loadState.refresh is LoadState.Error) {
                errorToast(context)
            }
        }
    }
}

@Composable
fun RepoRow(repo: RepositoryEntity?) {
    repo ?: return
    Row(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = repo.name)
                    Row {
                        Text(text = repo.stargazerCount.toString())
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Default.Star,
                            tint = Color.Yellow,
                            contentDescription = null,
                        )
                    }
                }
                if (repo.description != null) {
                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = repo.description,
                        color = Grey.copy(alpha = 0.8f),
                    )
                }
            }
        }
    }
}

@Composable
fun Error(onRetryClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { onRetryClick() },
            modifier = Modifier
                .clip(CircleShape)
                .align(alignment = Alignment.Center)
                .padding(top = 16.dp)
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = null,
            )
        }
    }
}