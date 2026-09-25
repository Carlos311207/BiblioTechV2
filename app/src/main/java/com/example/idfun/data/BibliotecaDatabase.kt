package com.example.idfun.data
/*
// ---------------- IMPORTACIONES ----------------


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bibliotech.model.Estudiante


// Importamos la entidad Libro
import com.example.bibliotech.model.Libro


// ----------------------------------------------------
// BASE DE DATOS PRINCIPAL
// Esta clase representa la base de datos SQLite
// utilizada por toda la aplicación.
// ----------------------------------------------------


@Database(


    // Lista de tablas que tendrá la base de datos
    entities = [Libro::class, Estudiante::class],






    // Primera versión de la base de datos
    //version = 1,
    version = 2,


    // No exportaremos el esquema durante el curso
    exportSchema = false
)
abstract class BibliotecaDatabase : RoomDatabase() {


    // El DAO se agregará en la siguiente guía.
    abstract fun libroDao(): LibroDao


    // =========================
    // DAO DE ESTUDIANTES
    // =========================
    abstract fun estudianteDao(): EstudianteDao


}
*//*
import android.content.Context
import androidx.room.Room


object DatabaseProvider {


   @Volatile
   private var INSTANCE: BibliotecaDatabase? = null


   fun getDatabase(context: Context): BibliotecaDatabase {
       return INSTANCE ?: synchronized(this) {
           val instance = Room.databaseBuilder(
               context.applicationContext,
               BibliotecaDatabase::class.java,
               "bibliotech_database"
           )
               .build()


           INSTANCE = instance
           instance
       }
   }
}*/


import android.content.Context


import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase




// ============================================================
// MIGRACIÓN DE LA BASE DE DATOS
// ============================================================
// La aplicación originalmente tenía la versión 1 de Room,
// que contenía la tabla de Libros.
//
// Ahora tenemos la versión 2 porque agregamos la tabla
// de Estudiantes.
//
// Esta migración crea la nueva tabla sin eliminar los
// libros que ya existen.
// ============================================================
val MIGRATION_1_2 = object : Migration(1, 2) {


    override fun migrate(
        db: SupportSQLiteDatabase
    ) {


        db.execSQL(
            """
           CREATE TABLE IF NOT EXISTS `Estudiantes` (
               `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
               `carnet` TEXT NOT NULL,
               `nombres` TEXT NOT NULL,
               `apellidos` TEXT NOT NULL,
               `grado` TEXT NOT NULL,
               `seccion` TEXT NOT NULL,
               `activo` INTEGER NOT NULL
           )
           """.trimIndent()
        )
    }
}




// ============================================================
// PROVEEDOR DE LA BASE DE DATOS
// ============================================================
object DatabaseProvider {


    @Volatile
    private var INSTANCE: BibliotecaDatabase? = null




    fun getDatabase(
        context: Context
    ): BibliotecaDatabase {


        return INSTANCE ?: synchronized(this) {


            val instance = Room.databaseBuilder(


                // Contexto de la aplicación
                context.applicationContext,


                // Clase principal de nuestra base de datos
                BibliotecaDatabase::class.java,


                // Nombre del archivo SQLite
                "bibliotech_database"


            )


                // ====================================================
                // REGISTRAMOS LA MIGRACIÓN
                // ====================================================
                // Le indicamos a Room cómo pasar de la versión 1
                // a la versión 2.
                .addMigrations(MIGRATION_1_2)


                // Construimos la base de datos
                .build()




            INSTANCE = instance


            instance
        }
    }
}


