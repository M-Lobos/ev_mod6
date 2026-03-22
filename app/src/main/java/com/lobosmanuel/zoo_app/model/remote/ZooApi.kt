package com.lobosmanuel.zoo_app.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//puntos de entrada para conssumo de datos
interface ZooApi {
    // 1. Obtiene la lista completa (resumen)
    @GET("es/animales")
    suspend fun fetchZooAnimals(): Response<List<ZooAnimalData>>

    // 2. Obtiene el detalle de uno solo
    @GET("es/animales/{id}")
    suspend fun fetchAnimalDetail(@Path("id") id: Int): Response<ZooAnimalData>
}