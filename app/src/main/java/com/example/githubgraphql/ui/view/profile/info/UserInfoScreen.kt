package com.example.githubgraphql.ui.view.profile.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.githubgraphql.data.local.entity.UserEntity
import com.example.githubgraphql.ui.components.Loading
import com.example.githubgraphql.ui.components.errorToast
import com.example.githubgraphql.ui.view.profile.ProfileViewModel
import com.example.githubgraphql.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Composable
fun UserInfoScreen(viewModel: ProfileViewModel) {
    val user = viewModel.user.observeAsState()
    UserInfoSection(viewModel, userResult = user.value)
}

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Composable
fun UserInfoSection(viewModel: ProfileViewModel, userResult: Resource<UserEntity>?) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userResult.apply {
            if ((userResult is Resource.Success || userResult is Resource.Error || userResult is Resource.Loading) && userResult.data != null) {
                Card(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    elevation = 4.dp,
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = userResult.data.avatarUrl,
                            builder = {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            },
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = userResult.data.name ?: ""
                )
                if (userResult.data.bio != null) {
                    Row(modifier = Modifier.padding(top = 16.dp)) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            imageVector = Icons.Default.Info,
                            contentDescription = null
                        )
                        Text(text = userResult.data.bio)
                    }
                }
                if (userResult.data.email != null) {
                    Row(modifier = Modifier.padding(top = 16.dp)) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                        Text(text = userResult.data.email)
                    }
                }
                if (userResult.data.company != null) {
                    Row(modifier = Modifier.padding(top = 16.dp)) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            imageVector = Icons.Default.Build,
                            contentDescription = null
                        )
                        Text(text = userResult.data.company)
                    }
                }
                if (userResult.data.location != null) {
                    Row(modifier = Modifier.padding(top = 16.dp)) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null
                        )
                        Text(text = userResult.data.location)
                    }
                }
                if (userResult is Resource.Error) {
                    Error(onRetryClick = { viewModel.refreshProfile() })
                    errorToast(context)
                }
                if (userResult is Resource.Loading) {
                    Loading()
                }
            } else if (userResult is Resource.Error && userResult.data == null) {
                Error(onRetryClick = { viewModel.refreshProfile() })
                errorToast(context)
            } else if (userResult is Resource.Loading) {
                Loading()
            }
        }
    }
}

@Composable
fun Error(onRetryClick: () -> Unit) {
    IconButton(
        onClick = { onRetryClick() },
        modifier = Modifier
            .clip(CircleShape)
            .padding(top = 16.dp)
    ) {
        Icon(
            Icons.Default.Refresh,
            contentDescription = null,
        )
    }
}