package com.example.examenpractico


import HelperDB
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examenpractico.Model.Usuario
import com.example.examenpractico.Model.Usuario.Companion.COL_ID
import com.example.examenpractico.Model.Usuario.Companion.COL_NOMBRE
import com.example.examenpractico.Model.Usuario.Companion.COL_PASSWORD
import com.example.examenpractico.Model.Usuario.Companion.COL_TIPO
import com.example.examenpractico.Model.Usuario.Companion.COL_USER


class MainActivity : AppCompatActivity() {

    private var managerUsuario: Usuario? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var etUser: TextView
    private lateinit var etPassword: TextView
    private lateinit var btnIniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUser = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnIniciar = findViewById(R.id.btnIniciarSesion)
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerUsuario = Usuario(this)
        btnIniciar.setOnClickListener {
            login()
        }
    }

    private fun login() {
       cursor = managerUsuario!!.login(etUser.text.toString().trim(),etPassword.text.toString().trim())
        if(cursor != null && cursor!!.moveToFirst()){
            val id = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_ID))
            val nombre = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_NOMBRE))
            val user = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_USER))
            val password = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_PASSWORD))
            val tipo = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_TIPO))

            if(tipo == "ADMIN"){
                val intent = Intent(this,HomeAdmin::class.java)
                intent.putExtra("nombreUsuario", nombre)
                intent.putExtra("user",user)
                intent.putExtra("tipo", tipo)
                startActivity(intent)
            }
            else if(tipo == "CLIENT"){
                val intent = Intent(this,ClientActivity::class.java)
                intent.putExtra("IDUSUARIO",id)
                intent.putExtra("nombreUsuario", nombre)
                intent.putExtra("user",user)
                intent.putExtra("tipo", tipo)
                startActivity(intent)
            }
        }
        else{
            Toast.makeText(this,"NO ENCONTRADO", Toast.LENGTH_SHORT).show()
        }
    }
}