package com.example.examenpractico.Model

import HelperDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Colores(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_CATEGORIA = "colores"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idcolores"
        val COL_NOMBRE = "descripcion"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_CATEGORIA = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORIA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(50) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        return valores
    }

    fun addNewColor(nombre: String?) {
        db!!.insert(TABLE_NAME_CATEGORIA, null, generarContentValues(nombre))
    }

    fun updateColor(id: Int, nombre: String?) {
        db!!.update(
            TABLE_NAME_CATEGORIA,
            generarContentValues(nombre),
            "${COL_ID}=?",
            arrayOf(id.toString())
        )
    }

    fun deleteColor(id: Int) {
        db!!.delete(TABLE_NAME_CATEGORIA, "${COL_ID}=?", arrayOf(id.toString()))
    }

    fun showColor(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME_CATEGORIA,
            columns,
            "$COL_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
    }

    fun searchIDColor(nombre: String): Int? {
        val columns = arrayOf(Colores.COL_ID, Colores.COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            Colores.TABLE_NAME_CATEGORIA, columns,
            "${Colores.COL_NOMBRE}=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }
    
    fun showAllColor(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME_CATEGORIA, columns, null, null, null, null, "$COL_NOMBRE ASC"
        )
    }
}