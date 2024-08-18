package com.example.examen02_mecanicaauto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    context: Context?
) : SQLiteOpenHelper(context, "MecanicaDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaMecanica =
            """
        CREATE TABLE Mecanica(
            idMecanica INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreMecanica VARCHAR(100),
            sectorMecanica VARCHAR(200),
            numeroEmpleadosMecanica INTEGER,
            abierto BOOLEAN,
            latitudMecanica REAL,
            longitudMecanica REAL
        );
            """.trimIndent()

        db?.execSQL(crearTablaMecanica)

        val crearTablaAuto =
            """
        CREATE TABLE Auto(
            idAuto INTEGER PRIMARY KEY AUTOINCREMENT,
            modeloAuto VARCHAR(100),
            estaListo BOOLEAN,
            valorCotizacion REAL,
            idMecanica INTEGER,
            FOREIGN KEY (idMecanica) REFERENCES Mecanica(idMecanica) ON DELETE CASCADE
        );
            """.trimIndent()

        db?.execSQL(crearTablaAuto)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerMecanicas(): ArrayList<MecanicaModel> {
        val lectureDB = readableDatabase
        val queryScript = """
        SELECT * FROM Mecanica
    """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<MecanicaModel>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    MecanicaModel(
                        queryResult.getInt(0),
                        queryResult.getString(1) ?: "",
                        queryResult.getString(2) ?: "",
                        queryResult.getInt(3),
                        queryResult.getInt(4) == 1,
                        queryResult.getDouble(5),
                        queryResult.getDouble(6)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun obtenerAutosPorMecanica(idMecanica: Int): ArrayList<AutoModel> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM Auto WHERE idMecanica=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(idMecanica.toString())
        )
        val response = arrayListOf<AutoModel>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    AutoModel(
                        queryResult.getInt(0),
                        queryResult.getString(1) ?: "",
                        queryResult.getInt(2) == 1,
                        queryResult.getDouble(3),
                        queryResult.getInt(4)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun crearMecanica(
        nombreMecanica: String,
        sectorMecanica: String,
        numeroEmpleadosMecanica: Int,
        abierto: Boolean,
        latitudMecanica: Double,
        longitudMecanica: Double
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombreMecanica", nombreMecanica)
        valuesToStore.put("sectorMecanica", sectorMecanica)
        valuesToStore.put("numeroEmpleadosMecanica", numeroEmpleadosMecanica)
        valuesToStore.put("abierto", if (abierto) 1 else 0)
        valuesToStore.put("latitudMecanica", latitudMecanica)
        valuesToStore.put("longitudMecanica", longitudMecanica)

        val storeResult = baseDatosEscritura.insert(
            "Mecanica",
            null,
            valuesToStore
        )
        baseDatosEscritura.close()

        return storeResult.toInt() != -1
    }

    fun actualizarMecanica(
        idMecanica: Int,
        nombreMecanica: String,
        sectorMecanica: String,
        numeroEmpleadosMecanica: Int,
        abierto: Boolean,
        latitudMecanica: Double,
        longitudMecanica: Double
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombreMecanica", nombreMecanica)
        valuesToUpdate.put("sectorMecanica", sectorMecanica)
        valuesToUpdate.put("numeroEmpleadosMecanica", numeroEmpleadosMecanica)
        valuesToUpdate.put("abierto", if (abierto) 1 else 0)
        valuesToUpdate.put("latitudMecanica", latitudMecanica)
        valuesToUpdate.put("longitudMecanica", longitudMecanica)

        val parametersUpdateQuery = arrayOf(idMecanica.toString())
        val updateResult = baseDatosEscritura.update(
            "Mecanica",
            valuesToUpdate,
            "idMecanica=?",
            parametersUpdateQuery
        )
        baseDatosEscritura.close()

        return updateResult != -1
    }

    fun crearAuto(
        modeloAuto: String,
        estaListo: Boolean,
        valorCotizacion: Double,
        idMecanica: Int
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("modeloAuto", modeloAuto)
        valuesToStore.put("estaListo", if (estaListo) 1 else 0)
        valuesToStore.put("valorCotizacion", valorCotizacion)
        valuesToStore.put("idMecanica", idMecanica)

        val storeResult = baseDatosEscritura.insert(
            "Auto",
            null,
            valuesToStore
        )
        baseDatosEscritura.close()

        return storeResult.toInt() != -1
    }

    fun actualizarAuto(
        idAuto: Int,
        modeloAuto: String,
        estaListo: Boolean,
        valorCotizacion: Double,
        idMecanica: Int
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("modeloAuto", modeloAuto)
        valuesToUpdate.put("estaListo", if (estaListo) 1 else 0)
        valuesToUpdate.put("valorCotizacion", valorCotizacion)
        valuesToUpdate.put("idMecanica", idMecanica)

        val parametersUpdateQuery = arrayOf(idAuto.toString())
        val updateResult = baseDatosEscritura.update(
            "Auto",
            valuesToUpdate,
            "idAuto=?",
            parametersUpdateQuery
        )
        baseDatosEscritura.close()

        return updateResult != -1
    }

    fun borrarMecanica(idMecanica: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametersDeleteQuery = arrayOf(idMecanica.toString())
        val deleteResult = baseDatosEscritura.delete(
            "Mecanica",
            "idMecanica=?",
            parametersDeleteQuery
        )
        baseDatosEscritura.close()

        return deleteResult != -1
    }

    fun borrarAuto(idAuto: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(idAuto.toString())
        val deleteResult = writeDB.delete(
            "Auto",
            "idAuto=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}
