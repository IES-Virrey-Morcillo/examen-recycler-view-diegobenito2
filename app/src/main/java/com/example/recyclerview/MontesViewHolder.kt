package com.example.recyclerview

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.ElementoMonteBinding

class MontesViewHolder (view: View):RecyclerView.ViewHolder(view) {
    val binding = ElementoMonteBinding.bind(view)

    fun render(item: Montes, OnClickDelete: (Int)->Unit) {
        binding.tvNombreMonte.text=item.nombre
        binding.tvAltura.text="Altura: " + item.altura
        binding.tvContinente.text="Continente: " + item.continente
        Glide.with(binding.imgvMonte.context).load(item.foto).into(binding.imgvMonte)
        binding.tvVerMas.setOnClickListener{
            Toast.makeText(binding.root.context, item.urlinfo, Toast.LENGTH_SHORT).show()
            openUrl(item.urlinfo)
        }
        binding.btnBorrar.setOnClickListener { OnClickDelete(adapterPosition) }

    }
    fun openUrl(url: String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
       binding.root.context.startActivity(intent)

    }

}
