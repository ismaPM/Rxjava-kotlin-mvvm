package com.example.jooycar.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jooycar.data.api.NetworkException
import com.example.jooycar.data.api.TimeOutException
import com.example.jooycar.data.model.response.BrandsResponse
import com.example.jooycar.data.model.response.ModelsResponse
import com.example.jooycar.data.repository.JooycarRepository
import com.example.jooycar.utils.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val jooycarRepository: JooycarRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _brands = MutableLiveData<Resource<List<BrandsResponse>>>()
    val brands: LiveData<Resource<List<BrandsResponse>>> get() = _brands
    private val _models = MutableLiveData<Resource<List<ModelsResponse>>>()
    val models: LiveData<Resource<List<ModelsResponse>>> get() = _models

    /**
     * Método para obtener marcas
     */
    fun getBrands() {
        _brands.postValue(Resource.loading(null))
        compositeDisposable.add(
        jooycarRepository.getBrands()
                //TODO:MEJORAR CON RXJAVA2 CON FLATMAP
                //.flatMap { brandResponse -> Observable.fromIterable(brandResponse) }
                //.flatMap { brand -> jooycarRepository.getModels(brand.id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //RESPUESTA CORRECTA
                    _brands.postValue(Resource.success(response))
                }, { throwable ->
                    when (throwable) {
                        is NetworkException -> {
                            _brands.postValue(Resource.noInternet(throwable.message, null))
                        }
                        is TimeOutException -> {
                            _brands.postValue(Resource.timeOut(throwable.message, null))
                        }
                        else -> {
                            _brands.postValue(Resource.throwError(throwable.message, null))
                        }
                    }
                })
        )
    }

    /**
     * Método para obtener modelos
     */
    fun getModels(id: String) {
        _models.postValue(Resource.loading(null))
        compositeDisposable.add(
            jooycarRepository.getModels(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //RESPUESTA CORRECTA
                    response.first().brandId = id
                    _models.value = (Resource.success(response))
                }, { throwable ->
                    when (throwable) {
                        is NetworkException -> {
                            _models.postValue(Resource.noInternet(throwable.message, null))
                        }
                        is TimeOutException -> {
                            _models.postValue(Resource.timeOut(throwable.message, null))
                        }
                        else -> {
                            _models.postValue(Resource.throwError(throwable.message, null))
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}