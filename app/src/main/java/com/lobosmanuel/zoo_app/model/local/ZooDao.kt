package com.lobosmanuel.zoo_app.model.local

import android.view.animation.Animation
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData

@Dao
interface ZooDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(animal: ZooAnimalData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnimals(animal: List<ZooAnimalData>)

    @Update
    suspend fun updateAnimals(animal: ZooAnimalData)

    @Delete
    suspend fun deleteAnimals(animal: ZooAnimalData)

    //TRAE TODOS LOS ANIMALES DE LA A A LA Z
    @Query ("SELECT * FROM animales ORDER BY ID DESC")
    fun getAllAnimals(): LiveData<List<ZooAnimalData>>

    //TRAE ANIMAL POR ID
    @Query ("SELECT * FROM animales WHERE id = :id")
    fun getAnimalById(id:Int): LiveData<ZooAnimalData>

    //ELIMINAR TERRENOS
    @Query("DELETE FROM animales")
    suspend fun deleteAll()
}