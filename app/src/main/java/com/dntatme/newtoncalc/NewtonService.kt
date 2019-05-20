package com.dntatme.newtoncalc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonService {

    @GET("/{operation}/{expression}")
    fun getResult(@Path("operation") operation : String, @Path("expression") expression : String): Call<Newton>
}


data class Newton(
    var operation: Any,
    var expression: Any,
    var result: Any?
)