package com.lobosmanuel.zoo_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lobosmanuel.zoo_app.model.ZooRepository
import com.lobosmanuel.zoo_app.model.local.ZooDataBase
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData
import kotlinx.coroutines.launch

// ViewModel que extiende de AndroidViewModel para poder usar el contexto de la aplicación
class ZooViewModel (application: Application): AndroidViewModel(application) {
    //1) REPOSITORIO: manejo de datos (room + API)

    private val repository : ZooRepository

    //2) lIVEDATA:
    // Datos obtenidosdesde internet
    lateinit var liveDataFromInternet : LiveData<List<ZooAnimalData>>
    // Datos almacenados en la DB local
    val allAnimals: LiveData<List<ZooAnimalData>>

    init {
        //obtener DAO desde la DB room
        val zooDao = ZooDataBase.getDataBase(application).getZooDao()
        // Línea 33 corregida

        repository = ZooRepository(zooDao) //se inicializa el repositorio pasando el DAO

        //Función que lanza para el respositorio
        viewModelScope.launch {
            repository.fetchDataFromInternetCoroutines()
        }

        allAnimals = repository.listAllTask     // Asigna el LiveData de la DB desde el repositorio

        liveDataFromInternet = repository.dataFromInternet  //Asigna el LiveData que contiene datos de internet


    }

    // Variable MutableLiveData para guardar el animal  seleccionado
    private var selectZooAnimal: MutableLiveData<ZooAnimalData> = MutableLiveData()

    // Variable MutableLiveData para detalla del animal seleccionado
    val animalDetail = MutableLiveData<ZooAnimalData>()

    fun getAnimalDetail(id: Int): LiveData<ZooAnimalData> {
        // 1. Lanzamos la descarga en segundo plano para llenar los campos null
        viewModelScope.launch {
            repository.fetchDetailFromInternet(id)
        }
        // 2. Retornamos el LiveData de Room (que se actualizará solo cuando la descarga termine)
        return repository.getAnimalById(id)
    }

    //Devuelve elemento seleccionado como LiveData (read only)
    fun selectedItem() : LiveData<ZooAnimalData> = selectZooAnimal

    //Funciones CRUD
    fun insertAnimal( animal : ZooAnimalData) = viewModelScope.launch {
        repository.insertAnimal(animal)
    }

    //Actualizar datos
    fun updateAnimal( animal: ZooAnimalData) = viewModelScope.launch {
        repository.updateAnimal(animal)
    }

    //Obtener datos por id
    fun getAnimalById(id: Int): LiveData<ZooAnimalData> {
        return repository.getAnimalById(id)
    }


}