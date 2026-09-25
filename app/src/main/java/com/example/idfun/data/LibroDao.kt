package com.example.idfun.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.idfun.modelo.Libro

@Dao
interface LibroDao {

    @Insert
    fun insertarLibro(libro: Libro): Long

    @Update
    fun actualizarLibro(libro: Libro)

    @Delete
    fun eliminarLibro(libro: Libro)

    @Query("SELECT * FROM libros")
    fun obtenerLibros(): List<Libro>

    @Query("SELECT * FROM libros WHERE id = :id")
    fun obtenerLibroPorId(id: Int): Libro?
}
