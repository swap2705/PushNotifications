package com.hsbc.pushnotifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddPayee {
    @SerializedName("benfAccountNumber")
    @Expose
    var benfAccountNumber: String? = null

    @SerializedName("benfName")
    @Expose
    var benfName: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("custId")
    @Expose
    var custId: String? = null

    @SerializedName("benfType")
    @Expose
    var benfType: String? = null

    @SerializedName("deviceId")
    @Expose
    var deviceId: String? = null
}