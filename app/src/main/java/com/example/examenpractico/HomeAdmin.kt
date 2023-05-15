package com.example.examenpractico

import HelperDB
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.examenpractico.Model.Automovil
import com.example.examenpractico.Model.Automovil.Companion.COL_ANIO
import com.example.examenpractico.Model.Automovil.Companion.COL_CAPACIDAD_ASIENTOS
import com.example.examenpractico.Model.Automovil.Companion.COL_DESCRIPCION
import com.example.examenpractico.Model.Automovil.Companion.COL_MODELO
import com.example.examenpractico.Model.Automovil.Companion.COL_NUMERO_ASIENTOS
import com.example.examenpractico.Model.Automovil.Companion.COL_NUMERO_CHASIS
import com.example.examenpractico.Model.Automovil.Companion.COL_NUMERO_MOTOR
import com.example.examenpractico.Model.Automovil.Companion.COL_NUMERO_VIN
import com.example.examenpractico.Model.Automovil.Companion.COL_PRECIO
import com.example.examenpractico.Model.Automovil.Companion.COL_URI_IMG
import com.example.examenpractico.Model.Colores
import com.example.examenpractico.Model.Marcas
import com.example.examenpractico.Model.TipoAutomovil

class HomeAdmin : AppCompatActivity() {

    private var manageAuto: Automovil? = null
    private var managerColor: Colores? = null
    private var managerMarca: Marcas? = null
    private var managerTipo: TipoAutomovil? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etIdAuto: EditText
    private lateinit var etModel: EditText
    private lateinit var etAño: EditText
    private lateinit var etNumeroVin: EditText
    private lateinit var etNumeroChasis:EditText
    private lateinit var etNumeroMotor: EditText
    private lateinit var etNumeroAsientos: EditText
    private lateinit var etCapacidadAsientos: EditText
    private lateinit var etPrecio:EditText
    private lateinit var etUrlImage:EditText
    private lateinit var etDescription: EditText
    private lateinit var cmbColores: Spinner
    private lateinit var cmbMarcas: Spinner
    private lateinit var cmbTipo:Spinner

    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBuscar: Button

    var catColores = ArrayList<String>()
    var catMarca = ArrayList<String>()
    var catTipo = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)
        val nombre = intent.getStringExtra("nombreUsuario")
        Toast.makeText(this@HomeAdmin,"BIENVENIDO $nombre",Toast.LENGTH_SHORT).show()

        etIdAuto = findViewById(R.id.eTIdAuto)
        etModel = findViewById(R.id.eTModelo)
        etAño = findViewById(R.id.eTAño)
        etNumeroVin = findViewById(R.id.etNumeroVin)
        etNumeroChasis = findViewById(R.id.etNumeroChasis)
        etNumeroMotor = findViewById(R.id.etNumeroMotor)
        etNumeroAsientos = findViewById(R.id.etNumeroAsientos)
        etCapacidadAsientos = findViewById(R.id.etCapacidadAsientos)
        etPrecio = findViewById(R.id.etPrecio)
        etUrlImage = findViewById(R.id.etURLImagen)
        etDescription = findViewById(R.id.etDescripcion)
        cmbColores = findViewById(R.id.cmbColores)
        cmbMarcas = findViewById(R.id.cmbMarcas)
        cmbTipo = findViewById(R.id.cmbTipoAuto)

        btnActualizar = findViewById(R.id.btnActualizar)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnEliminar = findViewById(R.id.btnEliminar)

        btnAgregar.setOnClickListener { agregar() }
        btnActualizar.setOnClickListener { actualizar() }
        btnBuscar.setOnClickListener { buscar() }
        btnEliminar.setOnClickListener { eliminar() }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageAuto = Automovil(this)
        managerColor = Colores(this)
        managerMarca = Marcas(this)
        managerTipo = TipoAutomovil(this)

        setSpinnerColores()
        setSpinnerMarcas()
        setSpinnerTipo()
    }

    fun setSpinnerColores() {
        // Cargando valores por defecto
        managerColor = Colores(this)
        cursor = managerColor!!.showAllColor()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            catColores.add("Seleccione")
            do {
                catColores.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, catColores)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbColores!!.adapter = adaptador
    }
    fun setSpinnerTipo() {
        // Cargando valores por defecto
        managerTipo = TipoAutomovil(this)
        cursor = managerTipo!!.showAllTipo()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            catTipo.add("Seleccione")
            do {
                catTipo.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item,catTipo)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbTipo!!.adapter = adaptador
    }
    fun setSpinnerMarcas() {
        // Cargando valores por defecto
        managerMarca = Marcas(this)
        cursor = managerMarca!!.showAllMarcas()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            catMarca.add("Seleccione")
            do {
                catMarca.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, catMarca)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbMarcas!!.adapter = adaptador
    }


    fun agregar(){
        val modelo = etModel!!.text.toString().trim()
        val año = etAño!!.text.toString().trim().toInt()
        val numeroVin = etNumeroVin.text.toString().trim()
        val numeroChasis = etNumeroChasis.text.toString().trim()
        val numeroMotor = etNumeroMotor.text.toString().trim()
        val numeroAsientos = etNumeroAsientos.text.toString().trim().toInt()
        val capacidadAsientos = etCapacidadAsientos.text.toString().trim().toInt()
        val precio = etPrecio.text.toString().trim().toDouble()
        val urlimg = etUrlImage.text.toString().trim()
        val decripcion = etDescription.text.toString().trim()
        var color = managerColor!!.searchIDColor(cmbColores.selectedItem.toString().trim())
        var marca = managerMarca!!.searchIDMarca(cmbMarcas.selectedItem.toString().trim())
        var tipo = managerTipo!!.searchIDTipo(cmbTipo.selectedItem.toString().trim())
        if(db != null){
            manageAuto!!.addNewCar(modelo,numeroVin,numeroChasis,numeroMotor,numeroAsientos,año,capacidadAsientos,precio,
            urlimg,decripcion,marca,tipo,color)
            clear()
            Toast.makeText(this@HomeAdmin,"Vehiculo agregado", Toast.LENGTH_SHORT).show()
        }
    }
    fun actualizar(){
        val id = etIdAuto!!.text.toString().trim().toInt()
        val modelo = etModel!!.text.toString().trim()
        val año = etAño!!.text.toString().trim().toInt()
        val numeroVin = etNumeroVin.text.toString().trim()
        val numeroChasis = etNumeroChasis.text.toString().trim()
        val numeroMotor = etNumeroMotor.text.toString().trim()
        val numeroAsientos = etNumeroAsientos.text.toString().trim().toInt()
        val capacidadAsientos = etCapacidadAsientos.text.toString().trim().toInt()
        val precio = etPrecio.text.toString().trim().toDouble()
        val urlimg = etUrlImage.text.toString().trim()
        val decripcion = etUrlImage.text.toString().trim()
        var color = managerColor!!.searchIDColor(cmbColores.selectedItem.toString().trim())
        var marca = managerMarca!!.searchIDMarca(cmbMarcas.selectedItem.toString().trim())
        var tipo = managerTipo!!.searchIDTipo(cmbTipo.selectedItem.toString().trim())
        if(db != null){
            manageAuto!!.updateCar(id,modelo,numeroVin,numeroChasis,numeroMotor,numeroAsientos,año,capacidadAsientos,precio,
                urlimg,decripcion,marca,tipo,color)
            clear()
            Toast.makeText(this@HomeAdmin,"Vehiculo actualizado", Toast.LENGTH_SHORT).show()
        }
    }
    fun eliminar(){
        val id = etIdAuto!!.text.toString().trim().toInt()
        manageAuto!!.deleteCar(id)
        clear()
        Toast.makeText(this@HomeAdmin,"Vehiculo eliminado", Toast.LENGTH_SHORT).show()
    }
    fun buscar(){
        val id = etIdAuto!!.text.toString().trim().toInt()
        if(db != null){
           cursor = manageAuto!!.showCar(id)
            if(cursor != null && cursor!!.moveToFirst()){
                etModel.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_MODELO)))
                etAño.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_ANIO)))
                etNumeroVin.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NUMERO_VIN)))
                etNumeroChasis.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_NUMERO_CHASIS)))
                etNumeroMotor.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_NUMERO_MOTOR)))
                etNumeroAsientos.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_NUMERO_ASIENTOS)))
                etCapacidadAsientos.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_CAPACIDAD_ASIENTOS)))
                etPrecio.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_PRECIO)))
                etUrlImage.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_URI_IMG)))
                etDescription.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    COL_DESCRIPCION)))
                val colorDefault = catColores.indexOf(cursor!!.getString(cursor!!.getColumnIndexOrThrow("color")))
                cmbColores.setSelection(colorDefault)
                val marcaDefault = catMarca.indexOf(cursor!!.getString(cursor!!.getColumnIndexOrThrow("marca")))
                cmbMarcas.setSelection(marcaDefault)
                val tipoDefault = catTipo.indexOf(cursor!!.getString(cursor!!.getColumnIndexOrThrow("tipo")))
                cmbTipo.setSelection(tipoDefault)
            }else{
                clear()
                Toast.makeText(this@HomeAdmin, "Registro no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun clear(){
        etModel.setText("")
        etAño.setText("")
        etNumeroVin.setText("")
        etNumeroChasis.setText("")
        etNumeroMotor.setText("")
        etNumeroAsientos.setText("")
        etCapacidadAsientos.setText("")
        etUrlImage.setText("")
        etPrecio.setText("")
        etDescription.setText("")

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.automoviles -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.marcas -> {
                val intent = Intent(this,MarcasActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.colores -> {
                val intent = Intent(this,ColoresActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.tipoAuto -> {
                val intent = Intent(this,TipoAutoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.usuarios -> {
                val intent = Intent(this,AdminUsuario::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}