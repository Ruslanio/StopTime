package com.stan.checker.core.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.stan.checker.util.date.DateManager
import com.stan.checker.util.date.Formatters
import java.time.LocalTime
import javax.inject.Inject

@ProvidedTypeConverter
class LocalTimeConverter @Inject constructor(
    private val dateManager: DateManager
) {

    @TypeConverter
    fun convertToTime(value: String?): LocalTime? {
        return value?.let {
            dateManager.parseToTime(it, Formatters.Time.dbConverter)
        }
    }

    @TypeConverter
    fun convertToString(value: LocalTime?): String? {
        return value?.let {
            dateManager.parseToString(it, Formatters.Time.dbConverter)
        }
    }
}