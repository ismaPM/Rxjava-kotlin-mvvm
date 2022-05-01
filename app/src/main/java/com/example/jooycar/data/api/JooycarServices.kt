package com.example.jooycar.data.api

import com.example.jooycar.data.model.response.BrandsResponse
import com.example.jooycar.data.model.response.ModelsResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface JooycarServices {

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("brands")
    fun getBrands(): Observable<List<BrandsResponse>>

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("models")
    fun getModels(@Query("brand") brand: String): Observable<List<ModelsResponse>>


}