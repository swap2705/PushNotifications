package com.hsbc.pushnotifications

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("/banking/add-individual")
    fun addPayee(@Body user: AddPayee?, @Header("X-Device-Token") header: String ): Call<Void>?

}