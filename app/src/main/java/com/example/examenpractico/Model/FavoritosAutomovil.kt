package com.example.examenpractico.Model

import HelperDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class FavoritosAutomovil(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_CATEGORIA = "favoritos_automovil"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idfavoritosautomovil"
        val COL_IDUSUARIO = "idusuario"
        val COL_IDFAVORITOAUTOMOVIL = "idfavoritoautomovil"
        val COL_FECHAAGREGADO = "fecha_agregado"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_CATEGORIA = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORIA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_IDUSUARIO + " integer NOT NULL,"
                        + COL_IDFAVORITOAUTOMOVIL + " integer NOT NULL,"
                        + COL_FECHAAGREGADO + " TIMESTAMP NOT NULL,"
                        + "FOREIGN KEY (idusuario) REFERENCES usuario(idusuario),"
                        + "FOREIGN KEY (idfavoritoautomovil) REFERENCES favoritos_automovil (idfavoritoautomovil));"
                )
    }

    // ContentValues
    fun generarContentValues(
        idusuario: Int?,
        idfavoritoautomovil: Int?,
        fechaagregado: String?,

        ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_IDUSUARIO, idusuario)
        valores.put(COL_IDFAVORITOAUTOMOVIL, idfavoritoautomovil)
        valores.put(COL_FECHAAGREGADO, fechaagregado)
        return valores
    }

    fun addFavorito(idusuario: Int?, idfavoritoautomovil: Int?, fechaagregado: String?) {
        db!!.insert(
            TABLE_NAME_CATEGORIA,
            null,
            generarContentValues(idusuario, idfavoritoautomovil, fechaagregado)
        )
    }

    fun showByUser(idusuario: String?): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_IDUSUARIO,
            COL_IDFAVORITOAUTOMOVIL,
            COL_FECHAAGREGADO,
            "automovil.${Automovil.COL_ANIO} as anio",
            "automovil.${Automovil.COL_PRECIO} as precio",
            "automovil.${Automovil.COL_URI_IMG} as img",
            "marcas.${Marcas.COL_NOMBRE} as marca",
            "tipo_automovil.${TipoAutomovil.COL_NOMBRE} as tipo"
        )
        val joinAuto =
            "LEFT JOIN favoritos_automovil on favoritos_automovil.idfavoritoautomovil = automovil.idautomovil"
        var joinMarca = "INNER JOIN automovil on automovil.idmarcas = marcas.idmarcas"
        var joinTipo = "INNER JOIN automovil on automovil.idtipoautomovil = tipo_automovil.idtipoautomovil"
        return db!!.query(
            "$TABLE_NAME_CATEGORIA $joinAuto $joinMarca $joinTipo",
            columns,
            "${COL_IDUSUARIO}=?",
            arrayOf(idusuario.toString()),
            null, null, null
        )
    }
    fun deleteFavorito(id:Int){
        db!!.delete(TABLE_NAME_CATEGORIA, "${COL_ID}=?", arrayOf(id.toString()))
    }

}