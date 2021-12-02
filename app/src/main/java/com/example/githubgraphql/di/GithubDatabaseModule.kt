package com.example.githubgraphql.di

import android.content.Context
import androidx.room.Room
import com.example.githubgraphql.data.local.GithubDatabase
import com.example.githubgraphql.data.local.dao.RepositoryDao
import com.example.githubgraphql.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GithubDatabaseModule {
    @Singleton
    @Provides
    fun provideGithubDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            "database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: GithubDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideDeviceDao(database: GithubDatabase): RepositoryDao = database.repositoryDao()
}