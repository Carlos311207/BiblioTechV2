package com.example.idfun.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.idfun.modelo.Estudiante
import com.example.idfun.modelo.Libro

@Database(
    entities = [Libro::class, Estudiante::class],
    version = 2,
    exportSchema = false
)
abstract class BibliotecaDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
    abstract fun estudianteDao(): EstudianteDao
}
