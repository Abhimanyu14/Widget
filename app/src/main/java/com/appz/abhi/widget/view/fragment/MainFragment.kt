package com.appz.abhi.widget.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.appz.abhi.widget.R
import com.appz.abhi.widget.databinding.MainFragmentBinding
import com.appz.abhi.widget.model.ApiInterface
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Response

class MainFragment : Fragment() {


    // Data binding
    private lateinit var mainFragmentBinding: MainFragmentBinding


    // On fragment view creation starting
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the fragment layout
        mainFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.main_fragment, container, false)

        // Return root view
        return mainFragmentBinding.root
    }


    // On fragment view creation completion
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI
        initUI()
    }

    // Initialize UI
    private fun initUI() {

        // Set click listener
        main_fragment_fetch_rate_button.setOnClickListener {

            val service = ApiInterface.create()
            val call: Call<JsonObject> = service.fetchRate()

            call.enqueue(object : retrofit2.Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val responseBody = response.body()
                    main_fragment_number_text_view.text = responseBody!!.get("number").toString()
                    main_fragment_text_text_view.text = responseBody.get("text").toString()
                }

                override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                    Log.e(TAG, throwable.message)
                }
            })
        }
    }


    companion object {


        // Logging constant
        private val TAG = MainFragment::class.java.simpleName
    }
}
