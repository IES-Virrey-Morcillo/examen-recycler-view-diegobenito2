package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MontesAdapter(
    private val montesList: MutableList<Montes>,
    private val OnClickDelete: (Int) -> Unit
) :
    RecyclerView.Adapter<MontesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MontesViewHolder(layoutInflater.inflate(R.layout.elemento_monte, parent, false))

    }

    override fun onBindViewHolder(holder: MontesViewHolder, position: Int) {
        val item = montesList[position]
        holder.render(item,OnClickDelete)

    }

    override fun getItemCount(): Int = montesList.size
    fun filterByName(montes: MutableList<Montes>) {
        montesList.clear()
        montesList.addAll(montes)
        notifyDataSetChanged()
    }


}
