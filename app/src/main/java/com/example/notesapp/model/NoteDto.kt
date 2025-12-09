package com.example.notesapp.model
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String,
    val title: String,
    val content: String,
    val category: String,
    val backgroundColor: Long,
    val isPinned: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val userId: String
)