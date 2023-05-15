package com.example.examenpractico.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examenpractico.Model.DataAutomovil
import com.example.examenpractico.R

class Adapter(var con: Context, private val dataList: List<DataAutomovil>,private val onClick: OnClickListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    interface OnClickListener{
        fun onClick(registro: DataAutomovil)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMarca: TextView = itemView.findViewById(R.id.tvMarca)
        var image = itemView.findViewById<ImageView>(R.id.cImageView)
        val tvAnio: TextView = itemView.findViewById(R.id.tvAnio)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvCapacidadAsientos: TextView = itemView.findViewById(R.id.tvCapacidadAsientos)
        val tvTipo: TextView = itemView.findViewById(R.id.tvTipoAuto)
        val btnFavorito: Button = itemView.findViewById(R.id.favorito)
        fun bind(dataAutomovil: DataAutomovil) {
            Picasso.get().load(dataAutomovil.img).into(image)
            tvMarca.text = "Marca: " + dataAutomovil.marca
            tvAnio.text = "Año: " + dataAutomovil.anio
            tvPrecio.text = "Precio: " + dataAutomovil.precio
            tvCapacidadAsientos.text = "Capacidad: " + dataAutomovil.capacidadA + "Personas"
            tvTipo.text = "Tipo: " + dataAutomovil.tipoAuto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carro = dataList[position]
        holder.bind(carro)
        holder.btnFavorito.setOnClickListener { onClick.onClick(carro) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}