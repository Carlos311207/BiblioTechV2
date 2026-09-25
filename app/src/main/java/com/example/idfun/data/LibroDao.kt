package com.example.idfun.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.idfun.modelo.Libro

@Dao
interface LibroDao {

    @Insert
    fun insertarLibro(libro: Libro): Long

    @Query("SELECT * FROM libros")
    fun obtenerLibros(): List<Libro>

    @Query("SELECT * FROM libros WHERE id = :id")
    fun obtenerLibroPorId(id: Int): Libro?
}