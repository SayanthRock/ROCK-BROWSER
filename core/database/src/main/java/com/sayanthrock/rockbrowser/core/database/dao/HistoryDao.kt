package com.sayanthrock.rockbrowser.core.database.dao
import androidx.room.Dao
import androidx.room.Query
import com.sayanthrock.rockbrowser.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
@Dao interface HistoryDao { @Query("SELECT * FROM history") fun getAllHistory(): Flow<List<HistoryEntity>> }
