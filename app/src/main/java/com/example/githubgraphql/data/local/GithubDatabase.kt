package com.example.githubgraphql.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubgraphql.data.local.dao.RemoteKeysDao
import com.example.githubgraphql.data.local.dao.RepositoryDao
import com.example.githubgraphql.data.local.dao.UserDao
import com.example.githubgraphql.data.local.entity.RemoteKeysEntity
import com.example.githubgraphql.data.local.entity.RepositoryEntity
import com.example.githubgraphql.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repositoryDao(): RepositoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}