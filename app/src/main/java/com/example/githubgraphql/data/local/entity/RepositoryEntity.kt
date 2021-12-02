package com.example.githubgraphql.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val stargazerCount: Int,
    val description: String? = null,
    val nextPageCursor: String? = null
)