package com.lobosmanuel.zoo_app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lobosmanuel.zoo_app.R
import com.lobosmanuel.zoo_app.databinding.FragmentFirstBinding
import com.lobosmanuel.zoo_app.viewModel.ZooViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    //    private var _binding: FragmentFirstBinding? = null
    private lateinit var _binding: FragmentFirstBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //instancia del viewModel
    private val viewModel: ZooViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instanciar el adapter
        val adapter = AdapterZoo()
        _binding.rvZooAnimal.adapter = adapter
        _binding.rvZooAnimal.layoutManager = GridLayoutManager(context, 1)

        //visualizacio´n del rv
        viewModel.liveDataFromInternet.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                Log.d("FirstF", "Datos recibidos desde internet: $it")
                adapter.setList(it)
            }
        })

        //Seleccion de un animal
        adapter.selectedAnimal.observe(viewLifecycleOwner) { selected ->

            selected?.let {
                //bundle para pasar datos al segundo fragmento
                val bundle = Bundle().apply {
                    putInt("id", it.id)
                    putString("imagen", it.imagen)
                }

                //crear instancia del fragmento
                val secondFragment = SecondFragment()
                secondFragment.arguments = bundle


                //reemplazar el fragmento
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, secondFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}