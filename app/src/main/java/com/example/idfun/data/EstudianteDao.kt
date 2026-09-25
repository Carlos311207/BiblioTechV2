package com.example.idfun.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.idfun.modelo.Estudiante

@Dao
interface EstudianteDao {

    @Insert
    fun insertarEstudiante(estudiante: Estudiante): Long

    @Query("SELECT * FROM estudiantes")
    fun obtenerEstudiantes(): List<Estudiante>

    @Query("SELECT * FROM estudiantes WHERE id = :id")
    fun obtenerEstudiantePorId(id: Int): Estudiante?
}