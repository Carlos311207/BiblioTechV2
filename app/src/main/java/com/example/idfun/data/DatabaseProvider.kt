package com.example.idfun.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: BibliotecaDatabase? = null

    fun getDatabase(context: Context): BibliotecaDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BibliotecaDatabase::class.java,
                "bibliotech_database"
            )
                // Como subimos de versión 1 a 2, esto borra y recrea
                // la BD si no hay una migración real. Está bien en desarrollo.
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}