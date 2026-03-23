package com.lobosmanuel.zoo_app.model.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room solo sabe guardar tipos primitivos (Int, String, Boolean). No sabe qué hacer con una List
 *
 * Se necesita un "traductor"
 * */

class TypeConverter {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value) // Convierte la lista a un String JSON
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType) // Convierte el String de vuelta a Lista
    }

}