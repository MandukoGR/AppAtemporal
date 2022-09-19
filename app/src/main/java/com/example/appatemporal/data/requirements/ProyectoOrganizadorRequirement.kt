package com.example.appatemporal.data.requirements

import android.util.Log
import com.example.appatemporal.data.localdatabase.entities.Estatus
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.domain.Repository

class ProyectoOrganizadorRequirement {

    suspend fun deleteProject(project: Proyecto, repository: Repository) {
        var projectToDelete = repository.getProyectoById(project.id_proyecto)
        repository.deleteProyecto(projectToDelete)
    }

    suspend fun updateProject(project: Proyecto, repository: Repository){
        var projectToUpdate = repository.getProyectoById(project.id_proyecto)
        projectToUpdate.nombre_proyecto = project.nombre_proyecto
        projectToUpdate.fecha_inicio = project.fecha_inicio

        repository.updateProyecto(projectToUpdate)
    }

    suspend fun getProjects(repository: Repository): List<Proyecto>{
        return repository.getAllProyectos()
    }

    fun countPendingActivities(repository: Repository, id_a: Int, id_e: Int): Int{
        return repository.countPendingActivities(id_a, id_e)
    }

    suspend fun insertEstatus(estatus: Estatus, repository:Repository){
        repository.insertEstatus(estatus)
    }
}