package com.sublimetech.supervisor.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
        .build()


    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val db = Firebase.firestore
        db.firestoreSettings = settings
        return db
    }

    @Singleton
    @get:Provides
    val firebaseStorage = Firebase.storage.apply {
        maxUploadRetryTimeMillis = 60000
    }
}