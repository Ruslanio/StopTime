package com.stan.checker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stan.checker.core.database.converters.LocalDateConverter
import com.stan.checker.core.database.converters.LocalTimeConverter
import com.stan.checker.core.database.dao.TaskDao
import com.stan.checker.core.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(value = [LocalDateConverter::class, LocalTimeConverter::class])
abstract class CheckerDatabase : RoomDatabase() {

    abstract fun tasksDao(): TaskDao
}