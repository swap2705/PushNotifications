package com.hsbc.pushnotifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdatePersonalDetails {
    @SerializedName("emailId")
    @Expose
    var emailId: String? = null

    @SerializedName("phoneNo")
    @Expose
    var phoneNo: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("pinCode")
    @Expose
    var pinCode: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("custId")
    @Expose
    var custId: String? = null

    @SerializedName("deviceId")
    @Expose
    var deviceId: String? = null
}