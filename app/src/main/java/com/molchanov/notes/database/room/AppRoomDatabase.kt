package com.molchanov.notes.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.molchanov.notes.model.AppNote

@Database(entities = [AppNote::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getAppRoomDao() : AppRoomDao

    companion object {
        //Volatile - база никогда не кэшируется, чтобы получать всегда актуальные значения
        @Volatile
        private var database : AppRoomDatabase? = null

        //Synchronized - к функции могут обращаться несоклько экземпляров одновременно
        @Synchronized
        fun getInstance(context: Context) : AppRoomDatabase{
            //Если база уже существует, возвращаем ее. Если нет - создаем новую
            return if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "database"
                ).build()
                database as AppRoomDatabase
            } else database as AppRoomDatabase
        }
    }
}