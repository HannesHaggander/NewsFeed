package com.example.newsreader.database

import android.content.Context
import androidx.room.*
import com.example.newsreader.database.dataaccess.ArticleItemDao
import com.example.newsreader.database.entities.ArticleItemEntity
import java.time.OffsetDateTime
import java.util.*

@Database(entities = [ArticleItemEntity::class], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun articleItemDao(): ArticleItemDao

    companion object {
        private const val DATABASE_NAME = "localdb"

        fun create(context: Context): LocalRoomDatabase = Room
            .databaseBuilder(context, LocalRoomDatabase::class.java, DATABASE_NAME)
            .build()
    }
}

object DataTypeConverters {

    @JvmStatic
    @TypeConverter
    fun uuidToString(uuid: UUID): String = uuid.toString()

    @JvmStatic
    @TypeConverter
    fun stringToUuid(value: String): UUID = UUID.fromString(value)

    @JvmStatic
    @TypeConverter
    fun offsetDateTimeToString(offsetDateTime: OffsetDateTime): String = offsetDateTime.toString()

    @JvmStatic
    @TypeConverter
    fun stringToOffsetDateTime(value: String): OffsetDateTime = OffsetDateTime.parse(value)

}
