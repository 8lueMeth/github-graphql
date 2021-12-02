package com.example.githubgraphql.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.example.githubgraphql.GetUserRepositoriesQuery
import com.example.githubgraphql.data.local.GithubDatabase
import com.example.githubgraphql.data.local.entity.RemoteKeysEntity
import com.example.githubgraphql.data.local.entity.RepositoryEntity
import javax.inject.Inject

@ExperimentalPagingApi
class RepositoriesRemoteMediator @Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val apolloClient: ApolloClient
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        val rowsCount = githubDatabase.repositoryDao().getRowsCount()
        return if (rowsCount > 0) InitializeAction.SKIP_INITIAL_REFRESH else InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        val repositoryDao = githubDatabase.repositoryDao()
        val remoteKeysDao = githubDatabase.remoteKeysDao()

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val response = apolloClient.query(
                GetUserRepositoriesQuery(
                    "blacksrc",
                    10,
                    Input.fromNullable(loadKey)
                )
            ).await()

            val edges = if (response.data?.user()?.repositories()?.edges()
                    .isNullOrEmpty()
            ) emptyList() else response.data?.user()?.repositories()?.edges()

            val nodes = edges?.map { repo ->
                RepositoryEntity(
                    id = repo.node()?.id().toString(),
                    name = repo.node()?.name() as String,
                    stargazerCount = repo.node()?.stargazerCount()!!,
                    description = repo.node()?.description(),
                    nextPageCursor = repo.cursor()
                )
            } ?: emptyList()

            val hasNextPage = response.data?.user()?.repositories()?.pageInfo()?.hasNextPage()

            githubDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    repositoryDao.clearAll()
                }

                val keys = nodes.map {
                    RemoteKeysEntity(
                        repoId = it.id,
                        nextKey = if (hasNextPage == true) it.nextPageCursor else null
                    )
                }

                remoteKeysDao.insertAll(keys)
                repositoryDao.insertAll(nodes)
            }

            MediatorResult.Success(endOfPaginationReached = hasNextPage == false)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        } catch (e: ApolloHttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepositoryEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo -> githubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id) }
    }
}
