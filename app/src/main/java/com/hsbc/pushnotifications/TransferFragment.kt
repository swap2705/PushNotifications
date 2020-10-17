package com.hsbc.pushnotifications

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_transfer.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFragment : Fragment() {
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

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val t = inflater.inflate(R.layout.fragment_transfer, container, false)
        val btnAddPayee = t.findViewById<Button>(R.id.buttonTransfer)
        val dateFormatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateFormatter.isLenient = false
        val today: String = dateFormatter.format(Date())
        btnAddPayee.setOnClickListener {
            val sharedPreferences =
                activity?.getSharedPreferences("sharedpreference", Context.MODE_PRIVATE)
            val deviceId = sharedPreferences?.getString("instance_token", "default")
            if (fromAccountNumber.text.isNullOrBlank() || benfName.text.isNullOrBlank() ||
                toAccountNumber.text.isNullOrBlank() || amount.text.isNullOrBlank() ||
                currency.text.isNullOrBlank() || name.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    activity,
                    "Please fill all the fields to make the transfer",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val transfer = Transfer()
                transfer.fromAccountNumber = fromAccountNumber.text.toString()
                transfer.benfName = benfName.text.toString()
                transfer.date = today.toString()
                transfer.toAccountNumber = toAccountNumber.text.toString()
                transfer.amount = amount.text.toString()
                transfer.currency = currency.text.toString()
                transfer.name= name.text.toString()
                transfer.custId = "testCustId123456"
                transfer.deviceId = deviceId
                if (deviceId == "default") {
                    Toast.makeText(
                        activity,
                        "Device Id not found, please clear your cache",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Call service here
                    transfer(transfer)
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
         * @return A new instance of fragment TransferFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun transfer(transfer: Transfer){
        val BASE_URL = "http://3.221.110.55:8081/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiInterface = retrofit.create(
            ApiInterface::class.java
        )
        transfer.deviceId?.let {
            apiService.transfer(transfer , it)?.enqueue(object : Callback<Void> {
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