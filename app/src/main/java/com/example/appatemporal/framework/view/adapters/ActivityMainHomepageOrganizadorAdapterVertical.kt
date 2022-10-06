package com.example.appatemporal.framework.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventsInMonth

class ActivityMainHomepageOrganizadorAdapterVertical(private val list: MutableList<EventModel>) : RecyclerView.Adapter<ActivityMainHomepageOrganizadorViewHolderVertical>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityMainHomepageOrganizadorViewHolderVertical {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityMainHomepageOrganizadorViewHolderVertical(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_grande_espectador, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityMainHomepageOrganizadorViewHolderVertical, position: Int) {
        val item = list[position]
        Log.d("listLog", list.toString())
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}