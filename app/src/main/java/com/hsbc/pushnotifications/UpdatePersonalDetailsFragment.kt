package com.hsbc.pushnotifications

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_transfer.*
import kotlinx.android.synthetic.main.fragment_transfer.fromAccountNumber
import kotlinx.android.synthetic.main.fragment_update_personal_details.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
            val request = JSONObject()
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
                request.put("emailId", emailId.text)
                request.put("phoneNo", phoneNo.text)
                request.put("country", country.text)
                request.put("city", city.text)
                request.put("pinCode", pinCode.text)
                request.put("address", address.text)
                request.put("custId", "testCustId123456")
                request.put("deviceId", deviceId)
                val header = JSONObject()
                header.put("X-Device-Token", deviceId)
                if (deviceId == "default") {
                    Toast.makeText(
                        activity,
                        "Device Id not found, please clear your cache",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        activity,
                        "Request: $request" + "Header: $header",
                        Toast.LENGTH_LONG
                    ).show()
                    //Call service here
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
}