package com.example.jooycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jooycar.data.api.RetrofitHelper
import com.example.jooycar.data.model.response.BrandsResponse
import com.example.jooycar.data.repository.JooycarRepository
import com.example.jooycar.databinding.FragmentHomeBinding
import com.example.jooycar.ui.home.adapter.BrandsAdapter
import com.example.jooycar.ui.home.factory.HomeFactory
import com.example.jooycar.ui.home.viewmodel.HomeViewModel
import com.example.jooycar.utils.Status

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val retrofit = RetrofitHelper.getRetrofitJooycar()
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: BrandsAdapter
    private var brandsResponse: List<BrandsResponse> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpViewModel()
        setRecycler()
        observers()
        launchBrands()
        return binding.root
    }

    /**
     * Inicializa el ViewModel
     */
    private fun setUpViewModel(){
        viewModel = ViewModelProvider(
            this,
            factory = HomeFactory(
                jooycarRepository = JooycarRepository(retrofit)
            )
        )[HomeViewModel::class.java]
    }

    /**
     *
     * Consume el servicio de obtener lista de marcas
     */
    private fun launchBrands(){
        viewModel.getBrands()
    }

    /**
     * Inicializa el recycler
     */
    private fun setRecycler() {
        adapter = BrandsAdapter(arrayListOf(),requireContext())
        binding.rvList.adapter = adapter
        //REFRESH LAYOUT
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getBrands()
        }
        //LISTENER ITEM CLICK
        adapter.onItemClick = { item ->
            item.id
        }
    }

    /**
     * Observers
     */
    private fun observers() {
        viewModel.brands.observe(viewLifecycleOwner) {
            it?.let {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.resource = it
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.lnBrands.visibility = View.GONE
                        binding.txtError.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.lnBrands.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.txtError.visibility = View.GONE
                        it.data?.let { brands ->
                            brandsResponse = brands
                            setData()
                        }
                    }
                    Status.ERROR -> {
                        binding.txtError.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.lnBrands.visibility = View.GONE
                    }
                    Status.NOINTERNET,Status.TIMEOUT ->{
                        binding.txtError.visibility = View.VISIBLE
                        binding.txtError.text = "TimeOut"
                        binding.progressBar.visibility = View.GONE
                        binding.lnBrands.visibility = View.GONE
                    }
                    Status.THROW ->{
                        binding.txtError.visibility = View.VISIBLE
                        binding.txtError.text = it.message
                        binding.progressBar.visibility = View.GONE
                        binding.lnBrands.visibility = View.GONE
                    }
                    else -> {
                        binding.txtError.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.lnBrands.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.models.observe(viewLifecycleOwner) {
            it?.let {
                binding.resource = it
                when (it.status) {
                    Status.SUCCESS -> {
                        if(!it.data.isNullOrEmpty()){
                            adapter.addModels(it.data.toMutableList())
                        }
                    }
                    Status.ERROR -> {

                    }
                    Status.NOINTERNET,Status.TIMEOUT ->{

                    }
                    Status.THROW ->{

                    }
                    else -> {

                    }
                }
            }
        }
    }

    /**
     * Muestra marcas
     */
    private fun setData(){
        if(!brandsResponse.isNullOrEmpty()){
            adapter.addList(brandsResponse.toMutableList())
            //TODO:MEJORAR CON RXJAVA2 CON FLATMAP
            for (brand in brandsResponse){
                viewModel.getModels(brand.id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}