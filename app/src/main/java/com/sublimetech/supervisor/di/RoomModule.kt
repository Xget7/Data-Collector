package com.sublimetech.supervisor.di

import android.content.Context
import com.sublimetech.supervisor.data.local.dao.FormsDao
import com.sublimetech.supervisor.data.local.dao.VisitDao
import com.sublimetech.supervisor.data.local.db.OfflineDatabase
import com.sublimetech.supervisor.data.local.db.OfflineDatabase.Companion.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideOfflineDatabase(@ApplicationContext context: Context): OfflineDatabase {
        return getDatabase(context)
    }


    @Provides
    fun provideVisitDao(database: OfflineDatabase): VisitDao {
        return database.getVisitDao()
    }

    @Provides
    fun provideFormDao(database: OfflineDatabase): FormsDao {
        return database.getFormDao()
    }

}