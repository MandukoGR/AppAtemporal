package com.example.appatemporal.data.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "actividad_table", foreignKeys = [ForeignKey(entity = Estatus::class, parentColumns = ["id_estatus"], childColumns = ["id_estatus"])])
data class Actividad(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_actividad") val id_actividad: Int,
    @ColumnInfo(name = "id_estatus") val id_estatus: Int,
    @ColumnInfo(name = "nombre_actividad") val nombre_actividad: String,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "modified_at") val modified_at: String,
)