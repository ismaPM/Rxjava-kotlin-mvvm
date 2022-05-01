package com.example.jooycar.ui.home.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jooycar.data.repository.JooycarRepository
import com.example.jooycar.ui.home.viewmodel.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeFactory(
    private val jooycarRepository: JooycarRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(jooycarRepository = this.jooycarRepository) as T
        } else throw IllegalArgumentException("Class HomeViewModel not found")
    }
}