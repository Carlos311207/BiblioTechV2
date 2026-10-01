package com.example.idfun.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.idfun.modelo.Prestamo

@Dao
interface PrestamoDao {

    @Insert
    fun insertarPrestamo(prestamo: Prestamo): Long

    @Update
    fun actualizarPrestamo(prestamo: Prestamo)

    @Delete
    fun eliminarPrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos WHERE devuelto = 0")
    fun obtenerPrestamosActivos(): List<Prestamo>

    @Query("SELECT * FROM prestamos WHERE id = :id")
    fun obtenerPrestamoPorId(id: Int): Prestamo?
}
