package ru.lonelywh1te.introgymapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.lonelywh1te.introgymapp.domain.ExerciseInfo

@Database(entities = [ExerciseInfo::class], version = 1)
abstract class MainDatabase: RoomDatabase() {



    companion object {
        private var db: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            if (db == null) {
                synchronized(MainDatabase::class.java) {
                    db = Room.databaseBuilder(context, MainDatabase::class.java, "app_db")
                        .fallbackToDestructiveMigration() // TODO: удалить
                        .build()
                }
            }

            return db!!
        }
    }
}