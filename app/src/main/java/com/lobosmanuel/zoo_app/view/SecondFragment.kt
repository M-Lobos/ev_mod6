package com.lobosmanuel.zoo_app.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


                        }
                    }

                    //función que define y acciona el fab para mandar email, recibe paramétro string nombre para config.
                    setupFab(it.nombre)

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

                    // Cargar la imagen con Glide
                    Glide.with(this)
                        .load(it.imagen)
                        .centerCrop()
                        .into(binding.ivDetalleAnimal)
                }
            }
        }
    }

    private fun setupFab(nombre: String) {
        binding.fabMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("info@tuzoologico.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Información sobre: $nombre")
                putExtra(Intent.EXTRA_TEXT, "Solicito más información sobre el o " +
                        "los Zoológicos dentro de Chile que tienen un $nombre. Me gustaría realizar " +
                        "una reserva para visitarlo junto a mi familia.")
            }

            try {
                startActivity(Intent.createChooser(intent, "Enviar correo..."))
            } catch (e: Exception) {
                Toast.makeText(context, "No se encontró app de correo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}