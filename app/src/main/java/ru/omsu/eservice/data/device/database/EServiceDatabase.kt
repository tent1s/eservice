package ru.omsu.eservice.data.device.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EducationCardData::class], version = 1, exportSchema = false)
abstract class EServiceDatabase : RoomDatabase() {

    abstract val educationCardDao: EducationCardDao

    companion object {

        @Volatile
        private var INSTANCE: EServiceDatabase? = null
        fun getInstance(context: Context): EServiceDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EServiceDatabase::class.java,
                        "schedule_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}