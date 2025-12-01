package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@Entity(tableName = "note_log")
data class Note_Log(
    @PrimaryKey val noteid:String,
    val userId:Int,
    val isSynced: Boolean = false,
)
