package com.example.githubgraphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.example.githubgraphql.ui.theme.GithubGraphQLTheme
import com.example.githubgraphql.ui.view.Screens
import com.example.githubgraphql.ui.view.profile.ProfileScreen
import com.example.githubgraphql.ui.view.profile.ProfileViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@ExperimentalPagingApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubGraphQLTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screens.ProfileScreen.route,
                    builder = {
                        addProfileScreen(navController = navController)
                    }
                )
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalPagingApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addProfileScreen(
    navController: NavHostController,
) {
    composable(
        route = Screens.ProfileScreen.route,
    ) {
        val viewModel: ProfileViewModel = hiltViewModel()
        ProfileScreen(viewModel = viewModel)
    }
}