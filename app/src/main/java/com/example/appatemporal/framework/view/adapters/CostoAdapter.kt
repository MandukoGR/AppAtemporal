package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.data.localdatabase.entities.Costo


class CostoAdapter(private val costoList: List<Costo>):
    RecyclerView.Adapter<CostoViewHolder>() {
    /**
     * In this function involves the View Holder of the Costo
     * @param parent, viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CostoViewHolder(layoutInflater.inflate(R.layout.item_costo, parent, false))
    }

    /**
     * This function creates a bind on each item in costo
     * @param CostoViewHolder, position
     */
    override fun onBindViewHolder(holder: CostoViewHolder, position: Int) {
        val item = costoList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = costoList.size



}