package com.lobosmanuel.zoo_app.model.local

import androidx.room.Database
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData

@Database(entities = [ZooAnimalData::class], version = 1 )
abstract class ZooDataBase {
    //DAO
    abstract fun zooDao(): ZooDao
}