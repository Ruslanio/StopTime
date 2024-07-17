package com.stan.checker.core.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.stan.checker.util.date.DateManager
import com.stan.checker.util.date.Formatters
import java.time.LocalDate
import javax.inject.Inject

@ProvidedTypeConverter
class LocalDateConverter @Inject constructor(
    private val dateManager: DateManager
) {

    @TypeConverter
    fun convertToDate(value: String?): LocalDate? {
        return value?.let {
            dateManager.parseToDate(it, Formatters.Date.dbConverter)
        }
    }

    @TypeConverter
    fun convertToString(value: LocalDate?): String? {
        return value?.let {
            dateManager.parseToString(it, Formatters.Date.dbConverter)
        }
    }
}