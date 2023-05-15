package com.example.examenpractico

import HelperDB
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examenpractico.Model.Colores
import com.example.examenpractico.Model.Colores.Companion.COL_NOMBRE

class ColoresActivity : AppCompatActivity() {

    private var manageColor: Colores? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etIdTipoColor: EditText
    private lateinit var etColor: EditText

    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBuscar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colores)

        etIdTipoColor = findViewById(R.id.etIdColor)
        etColor = findViewById(R.id.eTColor)

        btnAgregar = findViewById(R.id.btnAgregarColor)
        btnAgregar.setOnClickListener { agregar() }
        btnActualizar = findViewById(R.id.btnActualizarColor)
        btnActualizar.setOnClickListener { actualizar() }
        btnBuscar = findViewById(R.id.btnBuscarColor)
        btnBuscar.setOnClickListener { buscar() }
        btnEliminar = findViewById(R.id.btnEliminarColor)
        btnEliminar.setOnClickListener { eliminar() }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageColor = Colores(this)
    }




    fun agregar(){
        val nombre = etColor!!.text.toString().trim()
        if (db != null) {
            manageColor!!.addNewColor(nombre)
            clear()
            Toast.makeText(this@ColoresActivity, "Color agregada", Toast.LENGTH_SHORT).show()
        }
    }
    fun actualizar(){
        val idcolor = etIdTipoColor!!.text.toString().trim().toInt()
        val nombre = etColor!!.text.toString().trim()
        if(db != null){
            manageColor!!.updateColor(idcolor,nombre)
            clear()
            Toast.makeText(this@ColoresActivity, "Color actualizado", Toast.LENGTH_SHORT).show()
        }
    }
    fun eliminar(){
        val idcolor = etIdTipoColor!!.text.toString().trim().toInt()
        if(db != null){
            manageColor!!.deleteColor(idcolor)
            clear()
            Toast.makeText(this@ColoresActivity, "Color eliminado", Toast.LENGTH_SHORT).show()
        }
    }
    fun buscar(){
        val idcolor = etIdTipoColor!!.text.toString().trim().toInt()
        if(db != null){
            cursor = manageColor!!.showColor(idcolor)
            if(cursor != null && cursor!!.moveToFirst()){
                val nombre = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NOMBRE))
                etColor.setText(nombre)
            }
        }
        else{
            clear()
            Toast.makeText(this@ColoresActivity, "Registro no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
    fun clear(){
        etColor.setText("")
        etIdTipoColor.setText("")
    }

}