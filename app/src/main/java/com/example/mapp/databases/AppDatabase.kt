package com.example.mapp.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mapp.dao.MovieDao
import com.example.mapp.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlin.jvm.internal.Reflection
import kotlin.jvm.internal.Reflection.createKotlinClass

@Database(entities = [Movie::class], version = 1, exportSchema = false)
 abstract class AppDatabase : RoomDatabase() {

    companion object{
        private const val DB_NAME = "app_database"

        @Volatile
        private var instance:AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            if(instance == null){
                synchronized(this){
                    instance = create(context)
                }
            }

            return instance!!
        }
        private fun create(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
    abstract fun movieDao(): MovieDao
}