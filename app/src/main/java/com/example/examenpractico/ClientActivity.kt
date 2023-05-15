package com.example.examenpractico

import HelperDB
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenpractico.Adapter.Adapter
import com.example.examenpractico.Model.Automovil
import com.example.examenpractico.Model.Automovil.Companion.COL_ANIO
import com.example.examenpractico.Model.Automovil.Companion.COL_CAPACIDAD_ASIENTOS
import com.example.examenpractico.Model.Automovil.Companion.COL_ID
import com.example.examenpractico.Model.Automovil.Companion.COL_PRECIO
import com.example.examenpractico.Model.Automovil.Companion.COL_URI_IMG
import com.example.examenpractico.Model.Colores
import com.example.examenpractico.Model.DataAutomovil
import com.example.examenpractico.Model.FavoritosAutomovil
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ClientActivity : AppCompatActivity(), Adapter.OnClickListener{


    private var manageAuto: Automovil? = null
    private var manageFavorito: FavoritosAutomovil? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    lateinit var recyclerView: RecyclerView
    lateinit var adapterCar: Adapter

    var catAutos = ArrayList<DataAutomovil>()

    var iduser = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val nombre = intent.getStringExtra("nombreUsuario")
        iduser = intent.getStringExtra("IDUSUARIO").toString()
        Toast.makeText(this@ClientActivity,"BIENVENIDO $nombre", Toast.LENGTH_SHORT).show()

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageAuto = Automovil(this)
        manageFavorito = FavoritosAutomovil(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setAutos()

    }

    fun setAutos() {
        // Cargando valores por defecto
        manageAuto = Automovil(this)
        cursor = manageAuto!!.showAllCar()
        if (cursor != null && cursor!!.moveToFirst()) {

            do {
                val id= cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_ID)).toInt()
                val marca = cursor!!.getString(cursor!!.getColumnIndexOrThrow("marca"))
                val precio = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_PRECIO)).toDouble()
                val anio = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_ANIO)).toInt()
                val capacidadA = cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_CAPACIDAD_ASIENTOS)).toInt()
                val img = cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_URI_IMG))
                val color = cursor!!.getString(cursor!!.getColumnIndexOrThrow("color"))
                val tipo = cursor!!.getString(cursor!!.getColumnIndexOrThrow("tipo"))
                catAutos.add(DataAutomovil(id,marca,precio,anio,capacidadA,img,color,tipo))
            } while (cursor!!.moveToNext())
        }else{
            Toast.makeText(this@ClientActivity,"NO SE HAN ENCOTRADO AUTOMOVILES", Toast.LENGTH_SHORT).show()
        }

        adapterCar = Adapter(baseContext,catAutos,this)
        recyclerView!!.adapter = adapterCar
    }

    override fun onClick(registro: DataAutomovil) {
        val fecha = obtenerFechaActual()
        manageFavorito!!.addFavorito(iduser.toInt(),registro.id,fecha)
        Toast.makeText(this@ClientActivity,"Agregado a Favoritos", Toast.LENGTH_SHORT).show()
    }
    fun obtenerFechaActual(): String {
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaActual = Date()
        return formatoFecha.format(fechaActual)
    }
}