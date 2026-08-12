package com.sayanthrock.rockbrowser.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryEntry(entry: HistoryEntry)

    @Query("SELECT * FROM history_entries ORDER BY visitedAt DESC")
    fun getAllHistory(): Flow<List<HistoryEntry>>

    @Query("DELETE FROM history_entries WHERE id = :id")
    suspend fun deleteHistoryEntry(id: String)

    @Query("DELETE FROM history_entries")
    suspend fun clearHistory()
}
