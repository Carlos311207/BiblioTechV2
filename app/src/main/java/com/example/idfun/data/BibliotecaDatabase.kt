package com.example.idfun.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.idfun.modelo.Estudiante
import com.example.idfun.modelo.Libro
import com.example.idfun.modelo.Prestamo

@Database(
    entities = [Libro::class, Estudiante::class, Prestamo::class],
    version = 3,
    exportSchema = false
)
abstract class BibliotecaDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
    abstract fun estudianteDao(): EstudianteDao
    abstract fun prestamoDao(): PrestamoDao
}
