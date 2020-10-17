package com.hsbc.pushnotifications

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_update_personal_details.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePersonalDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatePersonalDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val t = inflater.inflate(R.layout.fragment_update_personal_details, container, false)
        val btnAddPayee = t.findViewById<Button>(R.id.buttonUpdateDetails)
        btnAddPayee.setOnClickListener {
            val sharedPreferences =
                activity?.getSharedPreferences("sharedpreference", Context.MODE_PRIVATE)
            val deviceId = sharedPreferences?.getString("instance_token", "default")
            if (emailId.text.isNullOrBlank() || phoneNo.text.isNullOrBlank() ||
                country.text.isNullOrBlank() || city.text.isNullOrBlank() ||
                pinCode.text.isNullOrBlank() || address.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    activity,
                    "Please fill all the fields to update your details",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val updatePersonalDetails = UpdatePersonalDetails()
                updatePersonalDetails.emailId = emailId.text.toString()
                updatePersonalDetails.phoneNo = phoneNo.text.toString()
                updatePersonalDetails.country = country.text.toString()
                updatePersonalDetails.city = city.text.toString()
                updatePersonalDetails.pinCode = pinCode.text.toString()
                updatePersonalDetails.address =  address.text.toString()
                updatePersonalDetails.custId = "testCustId123456"
                updatePersonalDetails.deviceId = deviceId
                if (deviceId == "default") {
                    Toast.makeText(
                        activity,
                        "Device Id not found, please clear your cache",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Call service here
                    updatePersonalDetails(updatePersonalDetails)
                }
            }
        }
        return t
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdatePersonalDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePersonalDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun updatePersonalDetails(updatePersonalDetails: UpdatePersonalDetails){
        val BASE_URL = "http://3.221.110.55:8081/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiInterface = retrofit.create(
            ApiInterface::class.java
        )
        updatePersonalDetails.deviceId?.let {
            apiService.updatePersonalDetails(updatePersonalDetails, it)?.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void?>?,
                    response: Response<Void?>
                ) {
                    val statusCode: Int = response.code()
                    Toast.makeText(
                        activity,
                        "Response code: $statusCode",
                        Toast.LENGTH_LONG
                    )
                }

                override fun onFailure(call: Call<Void?>?, th: Throwable?) {
                    // Log error here since request failed
                    th?.printStackTrace()
                }
            })
        }
    }

    fun getHttpClient(): OkHttpClient? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        //TODO : remove logging interceptors as it is to be used for development purpose
        return OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS).addInterceptor(logging).build()
    }
}