package com.example.githubgraphql.ui.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubgraphql.data.local.entity.RepositoryEntity
import com.example.githubgraphql.data.remote.repository.UserInfoRepository
import com.example.githubgraphql.data.remote.repository.UserReposRepository
import com.example.githubgraphql.util.Refresh
import com.example.githubgraphql.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class ProfileViewModel @Inject constructor(
    userReposRepository: UserReposRepository,
    userInfoRepository: UserInfoRepository,
) : ViewModel() {

    val repos: Flow<PagingData<RepositoryEntity>> =
        userReposRepository.getUserRepositories().cachedIn(viewModelScope)

    private val refreshTriggerChannel = Channel<Refresh>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    init {
        viewModelScope.launch { refreshTriggerChannel.send(Refresh.NORMAL) }
    }

    val user = refreshTrigger.flatMapLatest { userInfoRepository.getUserInfo() }.asLiveData()

    fun refreshProfile() {
        if (user.value !is Resource.Loading) {
            viewModelScope.launch {
                refreshTriggerChannel.send(Refresh.FORCE)
            }
        }
    }
}