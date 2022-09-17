package com.example.appatemporal.framework.view.adapters


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.databinding.ItemTodoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.AddActivity
import com.example.appatemporal.framework.view.DeleteActivity
import com.example.appatemporal.framework.view.ModificarActividad
import com.example.appatemporal.framework.viewModel.DeleteActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActividadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemTodoBinding.bind(view)

    fun render(activityModel: Actividad){
        binding.txtShowTitle.text = activityModel.nombre_actividad
        binding.ivEditIcon.setOnClickListener{
            val intent1 = Intent(itemView.context, ModificarActividad::class.java)
            with(intent1){
                putExtra("id_actividad", activityModel.id_actividad)
                putExtra("nombre_actividad", activityModel.nombre_actividad)
            }
            itemView.context.startActivity(intent1)
        }
        binding.txtShowTitle.setOnClickListener{
            val intent = Intent(itemView.context, AddActivity::class.java)
            with(intent){
                putExtra("id_actividad", activityModel.id_actividad)
                putExtra("nombre_actividad", activityModel.nombre_actividad)
            }
            itemView.context.startActivity(intent)
        }


        binding.deleteActivityButton.setOnClickListener{
            val intent = Intent(itemView.context, DeleteActivity::class.java)
            val viewModel = DeleteActivityViewModel()

            val repository = Repository(itemView.context)
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de que quieres eliminar este proyecto? Este proceso no puede revertirse")
            //builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Eliminar"){dialogInterface, which ->
                //Mandar a llamar la funcion delete()
                CoroutineScope(Dispatchers.IO ).launch {
                    viewModel.removeActividad(activityModel, repository)
                    //Toast.makeText(itemView.context, "Proyecto eliminado", Toast.LENGTH_SHORT).show()
                }
                itemView.context.startActivity(intent)


                //Toast.makeText(itemView.context,"Se eliminó el proyecto correctamente",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("Cancelar"){dialogInterface , which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}