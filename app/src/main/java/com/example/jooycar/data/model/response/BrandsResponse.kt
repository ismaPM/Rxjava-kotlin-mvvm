package com.example.jooycar.data.model.response

import com.google.gson.annotations.SerializedName

data class BrandsResponse (

    @SerializedName("id")
    var id: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("img")
    var img: String? = null,

    @SerializedName("created")
    var created: Long? = null,

    @SerializedName("modif")
    var modif: Long? = null,

    @SerializedName("country")
    var country: List<String> = arrayListOf(),

    @SerializedName("tags")
    var tags: List<String> = arrayListOf(),

    var models: List<ModelsResponse> = arrayListOf()


)
