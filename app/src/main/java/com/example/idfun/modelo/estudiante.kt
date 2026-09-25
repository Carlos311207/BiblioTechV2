package com.example.idfun.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")
data class Estudiante(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val carnet: String,
    val nombres: String,
    val apellidos: String,
    val grado: String,
    val seccion: String,
    val activo: Boolean
)