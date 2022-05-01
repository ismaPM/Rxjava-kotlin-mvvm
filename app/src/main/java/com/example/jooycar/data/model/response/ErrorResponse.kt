package com.example.jooycar.data.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

    @field:SerializedName("error")
    val error: Error? = null,

){
    data class Error (

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("statusCode")
        val statusCode: Int? = null,

        @field:SerializedName("errorCode")
        val errorCode: Int? = null,

        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("srcMessage")
        val srcMessage: String? = null
    )
}
