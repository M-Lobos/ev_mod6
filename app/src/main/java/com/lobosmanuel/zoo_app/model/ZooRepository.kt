package com.lobosmanuel.zoo_app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lobosmanuel.zoo_app.model.local.ZooDao
import com.lobosmanuel.zoo_app.model.remote.RetrofitClient
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData

class ZooRepository(private val zooDao: ZooDao) {
    //llamar método de conexión
    private val retrofitClient = RetrofitClient.getRetrofit()

    //referencia al POJO y la respuesta a recibir
    val dataFromInternet = MutableLiveData<List<ZooAnimalData>>()

    suspend fun fetchDataFromInternetCoroutines(){
        try {
            val response = retrofitClient.fetchZooAnimals()

            when(response.code()) {
                in 200..299 -> response.body()?.let { lista ->
                    Log.d("REPO", "¡Éxito! Llegaron ${lista.size} animales")
                    zooDao.insertAllAnimals(lista)
                    dataFromInternet.postValue(lista)
                }

                in 300..301 -> Log.d("REPO","${response.code()} --- ${response.errorBody()}")
                else ->  Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
            }
        } catch (t: Throwable) {
                Log.e("REPO", "Error ${t.message}")
        }
    }

    // Para el detalle de un animal específico
    val detailFromInternet = MutableLiveData<ZooAnimalData>()

    suspend fun fetchDetailFromInternet(id: Int) {
        try {
            val response = retrofitClient.fetchAnimalDetail(id) // segundo endpoint
            if (response.isSuccessful) {
                response.body()?.let { animal ->
                    // actualizar el animal en Room
                    // zooDao.insertAnimal(animal)
                    detailFromInternet.postValue(animal)
                }
            }
        } catch (t: Throwable) {
            Log.e("REPO", "Error en detalle: ${t.message}")
        }
    }


    fun getAnimalById(id: Int): LiveData<ZooAnimalData>{
        return zooDao.getAnimalById(id)
    }

    val listAllTask : LiveData<List<ZooAnimalData>> = zooDao.getAllAnimals()

    //insertar animal
    suspend fun insertAnimal (animal: ZooAnimalData){
        zooDao.insertAnimal(animal)
    }

    //actualizar animal
    suspend fun updateAnimal( animal: ZooAnimalData){
        zooDao.updateAnimals(animal)
    }

    //eliminar terreno
    suspend fun deleteAll(){
        zooDao.deleteAll()
    }
}