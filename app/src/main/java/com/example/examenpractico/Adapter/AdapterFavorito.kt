package com.example.examenpractico.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examenpractico.Model.Favorito
import com.example.examenpractico.R
import com.squareup.picasso.Picasso

class AdapterFavorito(var con: Context, private val dataList: List<Favorito>, private val onClick: OnClickListener) :
    RecyclerView.Adapter<AdapterFavorito.ViewHolder>() {

    interface OnClickListener{
        fun onClick(registro: Favorito)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMarca: TextView = itemView.findViewById(R.id.tvMarcaFavorito)
        var image = itemView.findViewById<ImageView>(R.id.cImageViewFavorito)
        val tvAnio: TextView = itemView.findViewById(R.id.tvAnioFavorito)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioFavorito)
        val tvTipo: TextView = itemView.findViewById(R.id.tvTipoAutoFavorito)
        val btnEliminar: Button = itemView.findViewById(R.id.eliminar)
        fun bind(dataAutomovil: Favorito) {
            Picasso.get().load(dataAutomovil.img).into(image)
            tvMarca.text = "Marca: " + dataAutomovil.marca
            tvAnio.text = "Año: " + dataAutomovil.anio
            tvPrecio.text = "Precio: " + dataAutomovil.precio
            tvTipo.text = "Tipo: " + dataAutomovil.tipo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carro = dataList[position]
        holder.bind(carro)
        holder.btnEliminar.setOnClickListener { onClick.onClick(carro) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}