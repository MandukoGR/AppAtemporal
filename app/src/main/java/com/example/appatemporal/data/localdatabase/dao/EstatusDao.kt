package com.example.appatemporal.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.appatemporal.data.localdatabase.entities.Estatus

@Dao
interface EstatusDao {
    // Get all estatus
    @Query("SELECT * FROM estatus_table")
    suspend fun getAll(): List<Estatus>

    // Insert all Estatus
    @Insert
    suspend fun insertAll(estatuss: List<Estatus>)

    // Insert one Estatus
    @Insert
    suspend fun insert(estatus: Estatus)

    // Get Estatus by id
    @Query("SELECT * FROM estatus_table WHERE id_estatus = :id")
    suspend fun getById(id: Int): Estatus

    // Delete all Estatuss
    @Query("DELETE FROM estatus_table")
    suspend fun deleteAll()

    // Delete a Estatus
    @Delete
    suspend fun delete(estatus: Estatus)
}