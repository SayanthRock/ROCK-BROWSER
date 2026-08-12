package com.sayanthrock.rockbrowser.core.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayanthrock.rockbrowser.core.database.dao.HistoryDao
import com.sayanthrock.rockbrowser.core.database.entity.HistoryEntity
@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class RockDatabase : RoomDatabase() { abstract fun historyDao(): HistoryDao }
