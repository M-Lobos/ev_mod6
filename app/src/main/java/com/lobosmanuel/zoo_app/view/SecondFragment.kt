package com.lobosmanuel.zoo_app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.lobosmanuel.zoo_app.R
import com.lobosmanuel.zoo_app.databinding.FragmentSecondBinding
import com.lobosmanuel.zoo_app.viewModel.ZooViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    // Usamos activityViewModels para compartir el estado con el FirstFragment
    private val viewModel: ZooViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Atrapamos el ID que enviamos desde el FirstFragment
        val animalId = arguments?.getInt("id") ?: -1

        if (animalId != -1) {
            // 2. Le decimos al ViewModel que busque los datos de este ID
            viewModel.getAnimalDetail(animalId)

            // 3. Observamos el LiveData que trae el detalle
            viewModel.getAnimalDetail(animalId).observe(viewLifecycleOwner) { animal ->
                animal?.let {
                    //nombre y especie
                    binding.tvDetalleNombre.text = it.nombre
                    binding.tvDetalleEspecie.text = it.especie

                    viewModel.getAnimalDetail(animalId).observe(viewLifecycleOwner) { animal ->
                        animal?.let {
                            Log.d("DETALLE", "Animal: ${it.nombre}, Habitat: ${it.habitat}, Desc: ${it.descripcion}")
                            // ... tu código de binding ...
                        }
                    }

                    //tarjeta chica | habitat dieta peso esperanza vida
                    binding.tvDetalleHabitat.text = it.habitat
                    binding.tvDetalleDieta.text = it.dieta
                    binding.tvDetallePeso.text = it.pesoPromedio
                    binding.tvDetalleVida.text = it.esperanzaDeVida

                    //Descripcion
                    binding.tvDetalleDescripcion.text = it.descripcion
                    //Comdias Favoritas
                    binding.tvDetalleComidas.text = it.comidasFavoritas.toString()
                    //predadoresNaturales
                    binding.tvPredadoresNaturales.text = it.predadoresNaturales.toString()
                    //Dato curiosos
                    binding.tvDetalleDatosCuriosos.text = it.datosCuriosos.toString()
                    //Conservación
                    binding.tvEstadoDeConservacion.text = it.estadoDeConservacion



                    // Cargamos la imagen con Glide
                    Glide.with(this)
                        .load(it.imagen)
                        .centerCrop()
                        .into(binding.ivDetalleAnimal)

                    // Configurar el FAB para el correo (lo vemos después)
                    //setupFab(it.nombre)
                }
            }
        }
    }

//    private fun setupFab(name: String) {
//        binding.fabMail.setOnClickListener {
//            // Aquí irá el Intent del correo
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}