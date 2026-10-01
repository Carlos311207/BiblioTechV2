package com.example.idfun.modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamos",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["id"],
            childColumns = ["idLibro"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Estudiante::class,
            parentColumns = ["id"],
            childColumns = ["idEstudiante"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idEstudiante: Int,
    val idLibro: Int,
    val fechaPrestamo: String,
    val fechaDevolucion: String? = null,
    val devuelto: Boolean = false,
    val fechaLimite: String
)






