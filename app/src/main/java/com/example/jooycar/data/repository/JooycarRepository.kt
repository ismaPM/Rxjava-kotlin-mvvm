package com.example.jooycar.data.repository

import com.example.jooycar.data.api.JooycarServices
import com.example.jooycar.data.model.response.BrandsResponse
import com.example.jooycar.data.model.response.ModelsResponse
import io.reactivex.Observable
import io.reactivex.Single

class JooycarRepository(private val jooycarServices: JooycarServices) {

    fun getBrands(): Observable<List<BrandsResponse>> =
        jooycarServices.getBrands()

    fun getModels(id: String): Observable<List<ModelsResponse>> =
        jooycarServices.getModels(id)

}