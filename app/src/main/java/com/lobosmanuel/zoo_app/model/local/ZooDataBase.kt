package com.lobosmanuel.zoo_app.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData

@Database(entities = [ZooAnimalData::class], version = 1 )
abstract class ZooDataBase: RoomDatabase() {
    //DAO
    abstract fun zooDao(): ZooDao

    companion object{
        @Volatile
        private var INSTANCE: ZooDataBase? = null

        fun getDataBase(context: Context): ZooDataBase {
            //Si ya existe una instancia, se retoma
            return INSTANCE?: synchronized(this){

                //si no existe, la crea
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZooDataBase::class.java,
                    "zoo_dataBase"
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }
}