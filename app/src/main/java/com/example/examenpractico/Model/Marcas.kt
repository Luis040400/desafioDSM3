package com.example.examenpractico.Model

import HelperDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Marcas(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_CATEGORIA = "marcas"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idmarcas"
        val COL_NOMBRE = "nombre"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_CATEGORIA =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORIA + "(" + COL_ID + " integer primary key autoincrement," + COL_NOMBRE + " varchar(50) NOT NULL);")
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        return valores
    }

    fun addNewMarca(nombre: String?) {
        db!!.insert(TABLE_NAME_CATEGORIA, null, generarContentValues(nombre))
    }

    fun updateMarca(id: Int, nombre: String?) {
        db!!.update(
            TABLE_NAME_CATEGORIA, generarContentValues(nombre), "$COL_ID=?", arrayOf(id.toString())
        )
    }
    fun searchIDMarca(nombre: String): Int? {
        val columns = arrayOf(Marcas.COL_ID, Marcas.COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            Marcas.TABLE_NAME_CATEGORIA, columns,
            "${Marcas.COL_NOMBRE}=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }
    fun deleteMarca(id: Int) {
        db!!.delete(TABLE_NAME_CATEGORIA, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun showMarca(id: Int): Cursor? {
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

    fun showAllMarcas(): Cursor?{
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME_CATEGORIA,columns,null,null,null,null,"$COL_NOMBRE ASC"
        )
    }
}