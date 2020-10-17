package com.hsbc.pushnotifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Transfer {
    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("custId")
    @Expose
    var custId: String? = null

    @SerializedName("deviceId")
    @Expose
    var deviceId: String? = null

    @SerializedName("fromAccountNumber")
    @Expose
    var fromAccountNumber: String? = null

    @SerializedName("benfName")
    @Expose
    var benfName: String? = null

    @SerializedName("toAccountNumber")
    @Expose
    var toAccountNumber: String? = null

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}