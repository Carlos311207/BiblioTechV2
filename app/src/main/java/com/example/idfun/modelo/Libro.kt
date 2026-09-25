package com.example.idfun.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val autor: String,
    val categoria: String,
    val anio: Int,
    val descripcion: String,
    val disponible: Boolean
)