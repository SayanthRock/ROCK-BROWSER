package com.sayanthrock.rockbrowser.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "history_entries")
data class HistoryEntry(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val url: String,
    val title: String,
    val visitedAt: Long = System.currentTimeMillis()
)
