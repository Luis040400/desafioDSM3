package com.example.examenpractico

import HelperDB
import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.examenpractico.Model.Usuario
import com.example.examenpractico.Model.Usuario.Companion.COL_APELLIDO
import com.example.examenpractico.Model.Usuario.Companion.COL_EMAIL
import com.example.examenpractico.Model.Usuario.Companion.COL_NOMBRE
import com.example.examenpractico.Model.Usuario.Companion.COL_PASSWORD
import com.example.examenpractico.Model.Usuario.Companion.COL_TIPO
import com.example.examenpractico.Model.Usuario.Companion.COL_USER

class AdminUsuario : AppCompatActivity() {

    private var manageUsuario: Usuario? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etId: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellido:EditText
    private lateinit var etCorreo: EditText
    private lateinit var etUser: EditText
    private lateinit var etContraseña: EditText
    private lateinit var spTipo: Spinner

    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBuscar: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_usuario)

        etId = findViewById(R.id.etIdTipoUsuario)
        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etCorreo = findViewById(R.id.etCorreo)
        etUser = findViewById(R.id.etUser)
        etContraseña = findViewById(R.id.etContraseña)
        spTipo = findViewById(R.id.spTipo)

        btnAgregar = findViewById(R.id.btnAgregarUsuario)
        btnAgregar.setOnClickListener { agregar() }
        btnActualizar = findViewById(R.id.btnActualizarUsuario)
        btnActualizar.setOnClickListener { actualizar() }
        btnBuscar = findViewById(R.id.btnBuscarUsuario)
        btnBuscar.setOnClickListener { buscar() }
        btnEliminar = findViewById(R.id.btnEliminarUsuario)
        btnEliminar.setOnClickListener { eliminar() }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageUsuario = Usuario(this)

        /*spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipo = parent!!.getItemAtPosition(position).toString().trim()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }*/

    }


    fun agregar(){
        val nombre = etNombre!!.text.toString().trim()
        val apellido = etApellido!!.text.toString().trim()
        val correo = etCorreo!!.text.toString().trim()
        val user = etUser!!.text.toString().trim()
        val contraseña = etContraseña!!.text.toString().trim()
        val tipo = spTipo.selectedItem.toString().trim()
        if(db != null){
            manageUsuario!!.addNewUser(nombre,apellido,correo,user,contraseña,tipo)
            clear()
            Toast.makeText(this@AdminUsuario,"Usuario agregado", Toast.LENGTH_SHORT).show()
        }
    }
    fun actualizar(){
        val idusuario = etId!!.text.toString().trim().toInt()
        val nombre = etNombre!!.text.toString().trim()
        val apellido = etApellido!!.text.toString().trim()
        val correo = etCorreo!!.text.toString().trim()
        val user = etUser!!.text.toString().trim()
        val contraseña = etContraseña!!.text.toString().trim()
        val tipo = spTipo.selectedItem.toString().trim()
        if(db != null){
            manageUsuario!!.updateUser(idusuario,nombre,apellido,correo,user,contraseña,tipo)
            clear()
            Toast.makeText(this@AdminUsuario,"Usuario actualizado", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminar(){
        val idusuario = etId!!.text.toString().trim().toInt()
        if(db != null){
            manageUsuario!!.deleteUser(idusuario)
            clear()
            Toast.makeText(this@AdminUsuario,"Usuario eliminado", Toast.LENGTH_SHORT).show()
        }
    }
    fun buscar(){
        val idusuario = etId!!.text.toString().trim().toInt()
        if(db != null){
            cursor = manageUsuario!!.showUser(idusuario)
            if(cursor != null && cursor!!.moveToFirst()){
                val nombre = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NOMBRE))
                val apellido = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_APELLIDO))
                val correo = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_EMAIL))
                val user = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_USER))
                val contraseña = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_PASSWORD))
                val tipo = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_TIPO))
                etNombre.setText(nombre)
                etApellido.setText(apellido)
                etCorreo.setText(correo)
                etUser.setText(user)
                etContraseña.setText(contraseña)
                val indiceElemento = resources.getStringArray(R.array.tipo_usuario).indexOf(tipo)
                spTipo.setSelection(indiceElemento)
            }
            else{
                clear()
                Toast.makeText(this@AdminUsuario, "Registro no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun clear(){
        etNombre.setText("")
        etApellido.setText("")
        etCorreo.setText("")
        etUser.setText("")
        etContraseña.setText("")
        spTipo.setSelection(0)
    }
}