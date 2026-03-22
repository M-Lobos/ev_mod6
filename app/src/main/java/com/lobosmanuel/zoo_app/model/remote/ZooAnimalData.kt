package com.lobosmanuel.zoo_app.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Objeto (entidad) animales
@Entity(tableName = "animales")
data class ZooAnimalData(
    @PrimaryKey
    @SerializedName("id") val id: Int, // En el JSON es un número
    @SerializedName("nombre") val nombre: String,
    @SerializedName("especie") val especie: String,
    @SerializedName("habitat") val habitat: String,
    @SerializedName("dieta") val dieta: String,
    @SerializedName("imagen") val imagen: String,

    // Campos que solo vienen en el detalle, 2do end point (pueden ser null inicialmente)
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("estadoDeConservacion") val estadoDeConservacion: String?,
    @SerializedName("esperanzaDeVida") val esperanzaDeVida: String?,
    @SerializedName("pesoPromedio") val pesoPromedio: String?,
    @SerializedName("alturaPromedio") val alturaPromedio: String?,

    // Listas (Requieren TypeConverter para Room)
    @SerializedName("datosCuriosos") val datosCuriosos: List<String>?,
    @SerializedName("comidasFavoritas") val comidasFavoritas: List<String>?,
    @SerializedName("predadoresNaturales") val predadoresNaturales: List<String>?
)
