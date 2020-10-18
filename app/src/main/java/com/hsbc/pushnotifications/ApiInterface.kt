package com.hsbc.pushnotifications

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("/banking/add-individual")
    fun addPayee(@Body addPayee: AddPayee?, @Header("X-Device-Token") header: String ): Call<Void>?

    @POST("/banking/personal-details-update")
    fun updatePersonalDetails(@Body updatePersonalDetails: UpdatePersonalDetails ?, @Header("X-Device-Token") header: String ): Call<Void>?

    @POST("/banking/confirm-payments")
    fun transfer(@Body transfer: Transfer?, @Header("X-Device-Token") header: String ): Call<Void>?
}