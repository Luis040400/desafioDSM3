package com.example.examenpractico

import HelperDB
import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenpractico.Adapter.Adapter
import com.example.examenpractico.Adapter.AdapterFavorito
import com.example.examenpractico.Model.Automovil
import com.example.examenpractico.Model.DataAutomovil
import com.example.examenpractico.Model.Favorito
import com.example.examenpractico.Model.FavoritosAutomovil
import com.example.examenpractico.Model.FavoritosAutomovil.Companion.COL_ID

class FavoritosActivity : AppCompatActivity() ,AdapterFavorito.OnClickListener{

    private var manageFavorito: FavoritosAutomovil? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    lateinit var recyclerView: RecyclerView
    lateinit var adapterFavorito: AdapterFavorito
    var iduser = ""
    var catFavoritos = ArrayList<Favorito>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        iduser = intent.getStringExtra("IDUSUARIO").toString()

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        manageFavorito = FavoritosAutomovil(this)

        recyclerView = findViewById(R.id.recyclerViewFavorito)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setFavorito()
    }

    fun setFavorito(){
       cursor = manageFavorito!!.showByUser(iduser)
        if(cursor != null && cursor!!.moveToFirst()){
            do {
                val id = cursor!!.getString(cursor!!.getColumnIndexOrThrow(COL_ID))
                val marca = cursor!!.getString(cursor!!.getColumnIndexOrThrow("marca"))
                val precio = cursor!!.getString(cursor!!.getColumnIndexOrThrow(Automovil.COL_PRECIO)).toDouble()
                val anio = cursor!!.getString(cursor!!.getColumnIndexOrThrow(Automovil.COL_ANIO)).toInt()
                val img = cursor!!.getString(cursor!!.getColumnIndexOrThrow(
                    "img"
                ))
                val tipo = cursor!!.getString(cursor!!.getColumnIndexOrThrow("tipo"))
                catFavoritos.add(Favorito(id.toInt(),img,marca,precio,anio,tipo))
            }while (cursor!!.moveToFirst())
        }else{
            Toast.makeText(this@FavoritosActivity,"NO SE HAN ENCOTRADO AUTOMOVILES", Toast.LENGTH_SHORT).show()
        }
        adapterFavorito = AdapterFavorito(baseContext,catFavoritos,this)

    }

    override fun onClick(registro: Favorito) {
        manageFavorito!!.deleteFavorito(registro.id)
        Toast.makeText(this@FavoritosActivity, "Favorito Eliminado", Toast.LENGTH_SHORT).show()
    }
}