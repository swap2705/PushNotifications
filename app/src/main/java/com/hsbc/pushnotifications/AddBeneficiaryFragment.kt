package com.hsbc.pushnotifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_add_beneficiary.*
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
 * Use the [AddBeneficiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBeneficiaryFragment : Fragment() {
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
        val t = inflater.inflate(R.layout.fragment_add_beneficiary, container, false)
        val spinner = t.findViewById<Spinner>(R.id.payee_spinner)
        val payeeArray = resources.getStringArray(R.array.payee_array)
        spinner?.adapter = activity?.applicationContext?.let { ArrayAdapter(
            it,
            R.layout.support_simple_spinner_dropdown_item,
            payeeArray
        ) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                println(type)
            }

        }

        val btnAddPayee = t.findViewById<Button>(R.id.buttonAddPayee)
        btnAddPayee.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences(
                "sharedpreference",
                Context.MODE_PRIVATE
            )
            val deviceId = sharedPreferences?.getString("instance_token", "default")
            if (accountNumber.text.isNullOrBlank() || payeeName.text.isNullOrBlank() || payee_spinner.selectedItem.toString()
                    .isBlank() || payeeCurrency.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    activity,
                    "Please fill all the fields to add payee",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val dateFormatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                dateFormatter.isLenient = false
                val today: String = dateFormatter.format(Date())
                val addPayeeRequest: AddPayee = AddPayee()
                addPayeeRequest.benfAccountNumber = accountNumber.text.toString()
                addPayeeRequest.benfName = payeeName.text.toString()
                addPayeeRequest.date = today
                addPayeeRequest.currency = payeeCurrency.text.toString()
                addPayeeRequest.custId = "testCustId123456"
                addPayeeRequest.benfType = payee_spinner.selectedItem.toString()
                addPayeeRequest.deviceId = deviceId
                if (deviceId == "default") {
                    Toast.makeText(
                        activity,
                        "Device Id not found, please clear your cache",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Call service here
                    addPayee(addPayeeRequest);
                }
            }
        }
        return  t
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddBeneficiaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddBeneficiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val TAG = "AddPayeeFragment"
    }

    fun addPayee(addPayeeRequest: AddPayee){
        val BASE_URL = "http://3.221.110.55:8081/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()


        val apiService: ApiInterface = retrofit.create(
            ApiInterface::class.java
        )
        addPayeeRequest.deviceId?.let {
            apiService.addPayee(addPayeeRequest, it)?.enqueue(object : Callback<Void> {
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
