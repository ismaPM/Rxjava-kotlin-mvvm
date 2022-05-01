package com.example.jooycar.data.model.response

import com.google.gson.annotations.SerializedName

data class ModelsResponse (

    var brandId: String = "0",

    @SerializedName("id")
    var id: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("brand")
    var brand: String = "",

    @SerializedName("created")
    var created: Long? = null,

    @SerializedName("modif")
    var modif: Long? = null,

    @SerializedName("subModels")
    var subModels: List<SubModels> = arrayListOf()

){
    data class SubModels (

        @SerializedName("id")
        var id: String = "",

        @SerializedName("year")
        var year: Int? = null,

        @SerializedName("fuelType")
        var fuelType: String = "",

        @SerializedName("compatibility")
        var compatibility: String = "",

        @SerializedName("modif")
        var modif: Long? = null
    )
}

