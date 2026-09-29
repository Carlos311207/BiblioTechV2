package com.example.idfun.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.idfun.modelo.Estudiante

@Dao
interface EstudianteDao {

    @Insert
    fun insertarEstudiante(estudiante: Estudiante): Long

    @Update
    fun actualizarEstudiante(estudiante: Estudiante)

    @Delete
    fun eliminarEstudiante(estudiante: Estudiante)

    @Query("SELECT * FROM estudiantes")
    fun obtenerEstudiantes(): List<Estudiante>

    @Query("SELECT * FROM estudiantes WHERE id = :id")
    fun obtenerEstudiantePorId(id: Int): Estudiante?
}
