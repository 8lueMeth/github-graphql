package com.example.githubgraphql.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubgraphql.data.local.entity.RepositoryEntity

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

    @Query("SELECT count(*) FROM repositories")
    suspend fun getRowsCount(): Int
}