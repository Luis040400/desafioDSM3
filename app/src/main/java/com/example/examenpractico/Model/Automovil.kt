package com.example.examenpractico.Model

import HelperDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Automovil(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_CATEGORIA = "automovil"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idautomovil"
        val COL_MODELO = "modelo"
        val COL_NUMERO_VIN = "numero_vin"
        val COL_NUMERO_CHASIS = "numero_chasis"
        val COL_NUMERO_MOTOR = "numero_motor"
        val COL_NUMERO_ASIENTOS = "numero_asientos"
        val COL_ANIO = "anio"
        val COL_CAPACIDAD_ASIENTOS = "capacidad_asientos"
        val COL_PRECIO = "precio"
        val COL_URI_IMG = "URI_IMG"
        val COL_DESCRIPCION = "descripcion"
        val COL_IDMARCAS = "idmarcas"
        val COL_IDTIPOAUTOMOVIL = "idtipoautomovil"
        val COL_IDCOLORES = "idcolores"


        //sentencia SQL para crear la tabla
        val CREATE_TABLE_CATEGORIA = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORIA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_MODELO + " varchar(50) NOT NULL,"
                        + COL_NUMERO_VIN + " varchar(50) NOT NULL,"
                        + COL_NUMERO_CHASIS + " varchar(50) NOT NULL,"
                        + COL_NUMERO_MOTOR + " varchar(50) NOT NULL,"
                        + COL_NUMERO_ASIENTOS + " integer NOT NULL,"
                        + COL_ANIO + " YEAR NOT NULL,"
                        + COL_CAPACIDAD_ASIENTOS + " integer NOT NULL,"
                        + COL_PRECIO + " decimal(10,2) NOT NULL,"
                        + COL_URI_IMG + " varchar(50) NOT NULL,"
                        + COL_DESCRIPCION + " varchar(50) NOT NULL,"
                        + COL_IDMARCAS + " integer NOT NULL,"
                        + COL_IDTIPOAUTOMOVIL + " integer NOT NULL,"
                        + COL_IDCOLORES + " integer NOT NULL,"
                        + "FOREIGN KEY (idmarcas) REFERENCES marcas(idmarcas),"
                        + "FOREIGN KEY (idtipoautomovil) REFERENCES tipo_automovil(idtipoautomovil),"
                        + "FOREIGN KEY (idcolores) REFERENCES colores (idcolores));"
                )
    }

    // ContentValues
    fun generarContentValues(
        modelo: String?,
        numeroVin: String?,
        numeroChasis: String?,
        numeroMotor: String?,
        numeroAsientos: Int?,
        anio: Int?,
        capacidadAsientos: Int?,
        precio: Double?,
        uriImg: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?

    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_MODELO, modelo)
        valores.put(COL_NUMERO_VIN, numeroVin)
        valores.put(COL_NUMERO_CHASIS, numeroChasis)
        valores.put(COL_NUMERO_MOTOR, numeroMotor)
        valores.put(COL_NUMERO_ASIENTOS, numeroAsientos)
        valores.put(COL_ANIO, anio)
        valores.put(COL_CAPACIDAD_ASIENTOS, capacidadAsientos)
        valores.put(COL_PRECIO, precio)
        valores.put(COL_URI_IMG, uriImg)
        valores.put(COL_DESCRIPCION, descripcion)
        valores.put(COL_IDMARCAS, idmarcas)
        valores.put(COL_IDTIPOAUTOMOVIL, idtipoautomovil)
        valores.put(COL_IDCOLORES, idcolores)
        return valores
    }

    fun addNewCar(
        modelo: String?,
        numeroVin: String?,
        numeroChasis: String?,
        numeroMotor: String?,
        numeroAsientos: Int?,
        anio: Int?,
        capacidadAsientos: Int?,
        precio: Double?,
        uriImg: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?
    ) {
        db!!.insert(
            TABLE_NAME_CATEGORIA, null, generarContentValues(
                modelo,
                numeroVin,
                numeroChasis,
                numeroMotor,
                numeroAsientos,
                anio,
                capacidadAsientos,
                precio,
                uriImg,
                descripcion,
                idmarcas,
                idtipoautomovil,
                idcolores
            )
        )
    }

    fun updateCar(
        id: Int, modelo: String?,
        numeroVin: String?,
        numeroChasis: String?,
        numeroMotor: String?,
        numeroAsientos: Int?,
        anio: Int?,
        capacidadAsientos: Int?,
        precio: Double?,
        uriImg: String?,
        descripcion: String?,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?
    ) {
        db!!.update(
            TABLE_NAME_CATEGORIA, generarContentValues(
                modelo,
                numeroVin,
                numeroChasis,
                numeroMotor,
                numeroAsientos,
                anio,
                capacidadAsientos,
                precio,
                uriImg,
                descripcion,
                idmarcas,
                idtipoautomovil,
                idcolores
            ), "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun deleteCar(id: Int) {
        db!!.delete(TABLE_NAME_CATEGORIA, "${COL_ID}=?", arrayOf(id.toString()))
    }

    fun showCar(id: Int): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_MODELO,
            COL_NUMERO_VIN,
            COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR,
            COL_NUMERO_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD_ASIENTOS,
            COL_PRECIO,
            COL_URI_IMG,
            "automovil.$COL_DESCRIPCION",
            "automovil.$COL_IDMARCAS",
            "automovil.$COL_IDTIPOAUTOMOVIL",
            "automovil.$COL_IDCOLORES",
            "marcas.${Marcas.COL_NOMBRE} as marca",
            "tipo_automovil.${TipoAutomovil.COL_NOMBRE} as tipo", "colores.${Colores.COL_NOMBRE
            } as color"
        )
        val joinMarca = "LEFT JOIN marcas on automovil.idmarcas = marcas.idmarcas"
        val joinColor = "LEFT JOIN colores on automovil.idcolores = colores.idcolores"
        val joinTipo =
            "LEFT JOIN tipo_automovil on automovil.idtipoautomovil = tipo_automovil.idtipoautomovil"
        return db!!.query(
            "$TABLE_NAME_CATEGORIA $joinMarca $joinTipo $joinColor",
            columns,
            "${COL_ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
    }

    fun showAllCar(): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_MODELO,
            COL_NUMERO_VIN,
            COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR,
            COL_NUMERO_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD_ASIENTOS,
            COL_PRECIO,
            COL_URI_IMG,
            "automovil.$COL_DESCRIPCION",
            "automovil.$COL_IDMARCAS",
            "automovil.$COL_IDTIPOAUTOMOVIL",
            "automovil.$COL_IDCOLORES",
            "marcas.${Marcas.COL_NOMBRE} as marca",
            "tipo_automovil.${TipoAutomovil.COL_NOMBRE} as tipo", "colores.${
                Colores.COL_NOMBRE
            } as color"
        )
        val joinMarca = "LEFT JOIN marcas on automovil.idmarcas = marcas.idmarcas"
        val joinColor = "LEFT JOIN colores on automovil.idcolores = colores.idcolores"
        val joinTipo =
            "LEFT JOIN tipo_automovil on automovil.idtipoautomovil = tipo_automovil.idtipoautomovil"
        return db!!.query(
            "$TABLE_NAME_CATEGORIA $joinMarca $joinTipo $joinColor",
            columns,
            null,
            null,
            null,
            null,
            null
        )
    }

}