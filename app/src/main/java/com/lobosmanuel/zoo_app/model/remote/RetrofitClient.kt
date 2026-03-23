package com.lobosmanuel.zoo_app.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Gestiona consumo de API para JSON -> GSON
class RetrofitClient {
    companion object{
        private const val BASE_URL = "https://zoo-api.vercel.app/"

        fun getRetrofit(): ZooApi{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ZooApi::class.java)
        }
    }
}