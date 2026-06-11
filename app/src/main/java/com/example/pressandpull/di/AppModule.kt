package com.example.pressandpull.di

import android.content.Context
import androidx.room.Room
import com.example.pressandpull.data.FitnessDao
import com.example.pressandpull.data.FitnessDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFitnessDatabase(@ApplicationContext context: Context): FitnessDatabase {
        return Room.databaseBuilder(
            context,
            FitnessDatabase::class.java,
            "press_and_pull.db"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideFitnessDao(database: FitnessDatabase): FitnessDao = database.fitnessDao()
}
