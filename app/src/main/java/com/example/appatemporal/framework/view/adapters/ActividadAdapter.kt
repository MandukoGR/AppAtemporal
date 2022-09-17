package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.data.localdatabase.entities.Actividad


class ActividadAdapter(private val activitiesList: List<Actividad>):
    RecyclerView.Adapter<ActividadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActividadViewHolder(layoutInflater.inflate(R.layout.item_todo, parent, false))
    }

    override fun onBindViewHolder(holder: ActividadViewHolder, position: Int) {
        val item = activitiesList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = activitiesList.size



}