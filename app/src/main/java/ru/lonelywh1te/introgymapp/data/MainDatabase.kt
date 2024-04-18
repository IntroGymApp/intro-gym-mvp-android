package ru.lonelywh1te.introgymapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.lonelywh1te.introgymapp.data.dao.ExerciseInfoDao
import ru.lonelywh1te.introgymapp.domain.ExerciseInfo

@Database(entities = [ExerciseInfo::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    abstract fun exerciseInfoDao(): ExerciseInfoDao

    companion object {
        private var db: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            if (db == null) {
                synchronized(MainDatabase::class.java) {
                    db = Room.databaseBuilder(context, MainDatabase::class.java, "app_db")
                        .createFromAsset("db/app_db")
                        .build()
                }
            }

            return db!!
        }
    }
}