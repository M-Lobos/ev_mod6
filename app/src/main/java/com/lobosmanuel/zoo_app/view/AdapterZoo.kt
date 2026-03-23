package com.lobosmanuel.zoo_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lobosmanuel.zoo_app.databinding.AnimalItemBinding
import com.lobosmanuel.zoo_app.model.remote.ZooAnimalData


class AdapterZoo : RecyclerView.Adapter<AdapterZoo.ZooViewHolder>() {

    private var listZooItems = listOf<ZooAnimalData>()
    val selectedAnimal = MutableLiveData<ZooAnimalData>()

    fun selectedAnimalItem(): LiveData<ZooAnimalData> = selectedAnimal

    fun setList(list: List<ZooAnimalData>) {
        listZooItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooViewHolder {

        val binding = AnimalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZooViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZooViewHolder, position: Int) {

        val animal = listZooItems[position]
        holder.bind(animal)
    }

    override fun getItemCount(): Int = listZooItems.size

    // El ViewHolder DEBE contener el onClick si implementa la interfaz
    inner class ZooViewHolder(private val binding: AnimalItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(zoo: ZooAnimalData) {
            // Glide carga la imagen
            Glide.with(binding.ivAnimal)
                .load(zoo.imagen)
                .centerCrop()
                .into(binding.ivAnimal)

            //agrega textos de atributos según tu POJO a la tarjeta
            binding.tvNombre.text = zoo.nombre
            binding.tvEspecie.text = zoo.especie
            binding.tvHabitat.text = zoo.habitat
            binding.chipDieta.text = zoo.dieta

            // Configurar el click en la vista entera
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            selectedAnimal.value = listZooItems[adapterPosition]
        }
    }
}