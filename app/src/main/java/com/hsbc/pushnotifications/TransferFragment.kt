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
            val request = JSONObject()
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
                request.put("fromAccountNumber", fromAccountNumber.text)
                request.put("benfName", benfName.text)
                request.put("date", today)
                request.put("toAccountNumber", toAccountNumber.text)
                request.put("amount", amount.text)
                request.put("currency", currency.text)
                request.put("name", name.text)
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
}