package com.example.examenpractico

import HelperDB
import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examenpractico.Model.Marcas
import com.example.examenpractico.Model.Marcas.Companion.COL_NOMBRE

class MarcasActivity : AppCompatActivity() {


    private var manageMarcas: Marcas? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etIdTipoMarca: EditText
    private lateinit var eTMarca: EditText

    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBuscar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcas)

        etIdTipoMarca = findViewById(R.id.etIdTipoMarca)
        eTMarca = findViewById(R.id.eTMarca)

        btnAgregar = findViewById(R.id.btnAgregarMarca)
        btnAgregar.setOnClickListener { agregar() }
        btnActualizar = findViewById(R.id.btnActualizarMarca)
        btnActualizar.setOnClickListener { actualizar() }
        btnBuscar = findViewById(R.id.btnBuscarMarca)
        btnBuscar.setOnClickListener { buscar() }
        btnEliminar = findViewById(R.id.btnEliminarMarca)
        btnEliminar.setOnClickListener { eliminar() }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageMarcas = Marcas(this)

    }


    fun agregar() {
        val nombre = eTMarca!!.text.toString().trim()
        if (db != null) {
            manageMarcas!!.addNewMarca(nombre)
            clear()
            Toast.makeText(this@MarcasActivity, "Marca agregada", Toast.LENGTH_SHORT).show()
        }
    }

    fun actualizar() {
        val idmarca = etIdTipoMarca!!.text.toString().trim().toInt()
        val nombre = eTMarca!!.text.toString().trim()
        if(db != null){
            manageMarcas!!.updateMarca(idmarca,nombre)
            clear()
            Toast.makeText(this@MarcasActivity, "Marca actualizada", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminar() {
        val idmarca = etIdTipoMarca!!.text.toString().trim().toInt()
        if(db != null){
            manageMarcas!!.deleteMarca(idmarca)
            clear()
            Toast.makeText(this@MarcasActivity, "Marca eliminada", Toast.LENGTH_SHORT).show()
        }
    }

    fun buscar() {
        val idmarca = etIdTipoMarca!!.text.toString().trim().toInt()
        if(db != null){
            cursor = manageMarcas!!.showMarca(idmarca)
            if(cursor != null && cursor!!.moveToFirst()){
                val nombre = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NOMBRE))
                eTMarca.setText(nombre)
            }
            else{
                clear()
                Toast.makeText(this@MarcasActivity, "Registro no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun clear(){
        eTMarca.setText("")
        etIdTipoMarca.setText("")
    }
}