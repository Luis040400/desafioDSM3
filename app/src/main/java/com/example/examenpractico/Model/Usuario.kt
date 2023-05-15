package com.example.examenpractico.Model

import HelperDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Usuario(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_CATEGORIA = "usuario"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idusuario"
        val COL_NOMBRE = "nombres"
        val COL_APELLIDO = "apellidos"
        val COL_EMAIL = "email"
        val COL_USER = "user"
        val COL_PASSWORD = "password"
        val COL_TIPO = "tipo"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_CATEGORIA = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORIA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(50) NOT NULL,"
                        + COL_APELLIDO + " varchar(50) NOT NULL,"
                        + COL_EMAIL + " varchar(50) NOT NULL,"
                        + COL_USER + " varchar(50) NOT NULL,"
                        + COL_PASSWORD + " varchar(50) NOT NULL,"
                        + COL_TIPO + " varchar(50) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?,

        ): ContentValues? {
        val valores = ContentValues()
        valores.put(Usuario.COL_NOMBRE, nombre)
        valores.put(Usuario.COL_APELLIDO, apellido)
        valores.put(Usuario.COL_EMAIL, email)
        valores.put(Usuario.COL_USER, user)
        valores.put(Usuario.COL_PASSWORD, password)
        valores.put(Usuario.COL_TIPO, tipo)
        return valores
    }

    fun login(user: String, password: String): Cursor? {
        var columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDO, COL_USER, COL_PASSWORD, COL_TIPO)
        return db!!.query(
            TABLE_NAME_CATEGORIA,
            columns,
            "$COL_USER=? AND $COL_PASSWORD=?",
            arrayOf(user, password),
            null,
            null,
            null
        )
    }

    fun addNewUser(
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ) {
        db!!.insert(
            TABLE_NAME_CATEGORIA, null, generarContentValues(
                nombre,
                apellido,
                email,
                user,
                password,
                tipo
            )
        )
    }

    fun updateUser(
        id: Int?,
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ) {
        db!!.update(
            TABLE_NAME_CATEGORIA, generarContentValues(
                nombre,
                apellido,
                email,
                user,
                password,
                tipo
            ), "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun deleteUser(id: Int?) {
        db!!.delete(TABLE_NAME_CATEGORIA, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun showUser(id: Int?): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_NOMBRE, COL_APELLIDO, COL_EMAIL, COL_USER, COL_PASSWORD,
            COL_TIPO
        )
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
}