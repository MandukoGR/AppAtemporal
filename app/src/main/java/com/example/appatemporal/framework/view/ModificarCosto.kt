package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.databinding.ModifyNewCostoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.DeleteCostoViewModel
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import kotlinx.coroutines.launch

class ModificarCosto : AppCompatActivity() {
    private val viewModel: DeleteCostoViewModel by viewModels()
    private lateinit var binding: ModifyNewCostoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModifyNewCostoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)
        var myExtras: Bundle? = intent.extras
        binding.newNameCosto.setText(myExtras?.getString("nombre_actividad"))
        binding.newMontoCosto.setText(myExtras?.getInt("monto").toString())
        val idProyecto: Int=  myExtras?.getInt("id_proyecto")?:-1
        val idcosto: Int = myExtras?.getInt("id_costo") ?: -1
        // Set click listener
        binding.newcostoBtn.setOnClickListener {
            val name = binding.newNameCosto.text.toString()
            val amount = binding.newMontoCosto.text.toString().toInt()
            val costo: Costo = Costo(idcosto, name, amount, idProyecto)

            lifecycleScope.launch {
                viewModel.updateCosto(costo, repository)
            }
            val intent = Intent(this, DeleteCosto::class.java)
            with(intent){
                putExtra("id_proyecto", idProyecto)
            }
            startActivity(intent)
        }

    }
}