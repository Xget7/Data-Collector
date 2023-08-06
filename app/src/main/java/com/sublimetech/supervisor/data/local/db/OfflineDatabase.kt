package com.sublimetech.supervisor.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sublimetech.supervisor.data.local.converters.Converters
import com.sublimetech.supervisor.data.local.dao.FormsDao
import com.sublimetech.supervisor.data.local.dao.VisitDao
import com.sublimetech.supervisor.domain.model.VisitEntity
import com.sublimetech.supervisor.domain.model.WomanForm.OfflineFormEntity

@Database(
    entities = [VisitEntity::class, OfflineFormEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OfflineDatabase : RoomDatabase() {

    abstract fun getVisitDao(): VisitDao
    abstract fun getFormDao(): FormsDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: OfflineDatabase? = null

        fun getDatabase(context: Context): OfflineDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OfflineDatabase::class.java,
                    "visit_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}