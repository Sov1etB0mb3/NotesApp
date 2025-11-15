package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: Int,
    val emailAddress:String,
    val name: String,
)
