package com.example.githubgraphql.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import com.example.githubgraphql.R
import com.example.githubgraphql.ui.view.profile.info.UserInfoScreen
import com.example.githubgraphql.ui.view.profile.repos.UserReposScreen
import com.google.accompanist.pager.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalPagerApi
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.main_page_top_bar_title)) },
                elevation = 0.dp,
            )
        },
    ) {
        TabScreen(viewModel)
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalPagerApi
@Composable
fun TabScreen(viewModel: ProfileViewModel) {
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(viewModel = viewModel, pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val pageList = listOf(
        stringResource(id = R.string.profile_pager_title),
        stringResource(id = R.string.repositories_pager_title)
    )
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            TabRowDefaults.Divider(
                thickness = 2.dp,
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
            )
        }
    ) {
        pageList.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        pageList[index].uppercase(),
                        color = if (pagerState.currentPage == index) Color.White else Color.White.copy(
                            alpha = ContentAlpha.disabled
                        )
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@ExperimentalPagerApi
@Composable
fun TabsContent(
    viewModel: ProfileViewModel,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = Modifier.background(MaterialTheme.colors.background),
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> UserInfoScreen(viewModel)
            1 -> UserReposScreen(viewModel)
        }
    }
}