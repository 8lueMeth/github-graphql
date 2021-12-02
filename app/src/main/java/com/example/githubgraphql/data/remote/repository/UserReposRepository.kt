package com.example.githubgraphql.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubgraphql.data.local.dao.RepositoryDao
import com.example.githubgraphql.data.local.entity.RepositoryEntity
import com.example.githubgraphql.data.remote.mediator.RepositoriesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class UserReposRepository
@Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val repositoriesRemoteMediator: RepositoriesRemoteMediator,
) {

    fun getUserRepositories(): Flow<PagingData<RepositoryEntity>> {
        val pagingSourceFactory = { repositoryDao.getAllRepositories() }

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = repositoriesRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}