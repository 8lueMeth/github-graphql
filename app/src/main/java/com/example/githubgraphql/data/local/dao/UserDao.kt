package com.example.githubgraphql.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubgraphql.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM users")
    fun getUser(): Flow<UserEntity>

    @Query("DELETE FROM users")
    suspend fun clear()
}