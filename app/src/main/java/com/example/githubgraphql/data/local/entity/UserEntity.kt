package com.example.githubgraphql.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val avatarUrl: String,
    val email: String? = null,
    val company: String? = null,
    val location: String? = null,
    val bio: String? = null,
)
