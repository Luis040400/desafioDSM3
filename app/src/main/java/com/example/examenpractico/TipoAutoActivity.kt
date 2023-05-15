package com.example.examenpractico

import HelperDB
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examenpractico.Model.TipoAutomovil
import com.example.examenpractico.Model.TipoAutomovil.Companion.COL_NOMBRE

class TipoAutoActivity : AppCompatActivity() {

    private var manageTipo: TipoAutomovil? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etId: EditText
    private lateinit var etTipo: EditText

    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBuscar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_auto)

        etId = findViewById(R.id.etIdTipoAuto)
        etTipo = findViewById(R.id.eTTipoAuto)

        btnActualizar = findViewById(R.id.btnActualizarTipoAuto)
        btnAgregar = findViewById(R.id.btnAgregarTipoAuto)
        btnBuscar = findViewById(R.id.btnBuscarTipoAuto)
        btnEliminar = findViewById(R.id.btnEliminarTipoAuto)

        btnActualizar.setOnClickListener { actualizar() }
        btnBuscar.setOnClickListener { buscar() }
        btnAgregar.setOnClickListener { agregar() }
        btnEliminar.setOnClickListener { eliminar() }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageTipo = TipoAutomovil(this)
    }



    fun agregar(){
        val nombre = etTipo!!.text.toString().trim()
        if(db != null){
            manageTipo!!.addNewTipo(nombre)
            clear()
            Toast.makeText(this@TipoAutoActivity,"Tipo agregado", Toast.LENGTH_SHORT).show()
        }
    }
    fun actualizar(){
        val idtipo = etId!!.text.toString().trim().toInt()
        val nombre = etTipo!!.text.toString().trim()
        if(db != null){
            manageTipo!!.updateTipo(idtipo,nombre)
            clear()
            Toast.makeText(this@TipoAutoActivity,"Tipo actualizado", Toast.LENGTH_SHORT).show()
        }
    }
    fun eliminar(){
        val idtipo = etId!!.text.toString().trim().toInt()
        if(db != null){
            manageTipo!!.deleteTipo(idtipo)
            clear()
            Toast.makeText(this@TipoAutoActivity,"Tipo eliminado", Toast.LENGTH_SHORT).show()
        }
    }
    fun buscar(){
        val idtipo = etId!!.text.toString().trim().toInt()
        if(db != null){
            cursor = manageTipo!!.showTipo(idtipo)
            if (cursor != null && cursor!!.moveToFirst()){
                val nombre = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NOMBRE))
                etTipo.setText(nombre)
            }
        }
        else{
            clear()
            Toast.makeText(this@TipoAutoActivity, "Registro no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
    fun clear(){
        etId.setText("")
        etTipo.setText("")
    }

}