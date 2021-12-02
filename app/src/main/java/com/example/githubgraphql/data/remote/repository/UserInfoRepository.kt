package com.example.githubgraphql.data.remote.repository

import androidx.room.withTransaction
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.example.githubgraphql.GetUserInfoQuery
import com.example.githubgraphql.data.local.GithubDatabase
import com.example.githubgraphql.data.local.entity.UserEntity
import com.example.githubgraphql.util.networkBoundResource
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val apolloClient: ApolloClient,
) {

    private val userDao = githubDatabase.userDao()

    fun getUserInfo() = networkBoundResource(
        query = { userDao.getUser() },
        fetch = {
            val userFromNetwork =
                apolloClient.query(GetUserInfoQuery("blacksrc")).await().data?.user()

            UserEntity(
                id = userFromNetwork?.id().toString(),
                name = userFromNetwork?.name(),
                avatarUrl = userFromNetwork?.avatarUrl().toString(),
                company = userFromNetwork?.company(),
                email = userFromNetwork?.email(),
                location = userFromNetwork?.location(),
                bio = userFromNetwork?.bio(),
            )
        },
        saveFetchResult = { userEntity ->
            githubDatabase.withTransaction {
                userDao.clear()
                userDao.insertUser(userEntity)
            }
        },
        shouldFetch = { true },
    )
}