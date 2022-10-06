package com.example.appatemporal.framework.view

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.*
import com.example.appatemporal.domain.models.EventModel01

class ActivityMisEventosOrganizadorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ActivityHomepageTarjetaEventosGrandeEspectadorBinding.bind(view)

    fun render(eventClass: EventModel01){
        binding.Nombre.text = eventClass.nombre
        binding.Lugar.text = eventClass.ubicacion
        binding.Direccion.text = eventClass.direccion

        var cardBtn = binding.cardEvent

        cardBtn.setOnClickListener{
            var Nombre : String = eventClass.nombre
            var Descripcion : String = eventClass.descripcion
            var Ubicacion : String = eventClass.ubicacion
            var Direccion : String = eventClass.direccion
            var Ciudad : String = eventClass.ciudad
            var Estado : String = eventClass.estado
            var IdEvento : String = eventClass.id


            val funcionPorEvento =  Intent(itemView.context, ActivityVisualizarEventoOrganizador::class.java)

            funcionPorEvento.putExtra("nombre", Nombre)
            funcionPorEvento.putExtra("descripcion", Descripcion)
            funcionPorEvento.putExtra("ubicacion", Ubicacion)
            funcionPorEvento.putExtra("direccion", Direccion)
            funcionPorEvento.putExtra("ciudad", Ciudad)
            funcionPorEvento.putExtra("estado", Estado)
            funcionPorEvento.putExtra("idEvento", IdEvento)

            itemView.context.startActivity(funcionPorEvento)
        }
    }
}