package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityMainHomepageEspectadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.ActivityMainHomepageEspectadorAdapterHorizontal
import com.example.appatemporal.framework.viewModel.GetEventsInMonthViewModel
import java.util.*

class ActivityMainHomepageEspectador : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomepageEspectadorBinding
    val getEventsInMonthViewModel : GetEventsInMonthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomepageEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository(this)

        val userIdTemp = "pod6xLDUeRNZItm7u93DC5CYbgJ2"
        val dia = getDay()
        val month = getMonth()

        initRecyclerViewHorizontal(getEventsInMonthViewModel, dia, month, repository)
        initRecyclerViewVertical(getEventsInMonthViewModel, dia, month, repository)

        binding.navbar.homeIcon.setOnClickListener {
            finish()
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            finish()
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

    }

    fun getDay() : Int {
        val cal = Calendar.getInstance()
        val dayOfMonth =  cal[Calendar.DAY_OF_MONTH]
        // Log.d("pruebasDia-Actual",dayOfMonth.toString())
        return dayOfMonth
    }

    fun getMonth() : Int {
        val cal = Calendar.getInstance()
        val numberOfMonth =  cal[Calendar.MONTH]+1
        // Log.d("pruebasMes-Actual",numberOfMonth.toString())
        return numberOfMonth
    }


    private fun initRecyclerViewHorizontal(getEventsInMonthViewModel: GetEventsInMonthViewModel, day: Int, month: Int, repository: Repository){
        getEventsInMonthViewModel.getEventsMonth(day, month, repository)
        //Log.d("LOG Activity",getEventsInMonthViewModel.getEventsMonth(day, month, repository).toString())
        getEventsInMonthViewModel.eventsMonth.observe(this, Observer { eventsList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.HORIZONTAL
            binding.HorizontalEspectadorView.layoutManager = LinearLayoutManager(this) // Le da el layout que usará el RV.
            binding.HorizontalEspectadorView.adapter = ActivityMainHomepageEspectadorAdapterHorizontal(eventsList)
        })
    }

    private fun initRecyclerViewVertical(getEventsInMonthViewModel: GetEventsInMonthViewModel, day: Int, month: Int, repository: Repository){
        getEventsInMonthViewModel.getEventsMonth(day, month, repository)
        //Log.d("LOG Activity",getEventsInMonthViewModel.getEventsMonth(day, month, repository).toString())
        getEventsInMonthViewModel.eventsMonth.observe(this, Observer { eventsList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.VERTICAL
            binding.VerticalView.layoutManager = LinearLayoutManager(this) // Le da el layout que usará el RV.
            binding.VerticalView.adapter = ActivityMainHomepageEspectadorAdapterHorizontal(eventsList)
        })
    }
}