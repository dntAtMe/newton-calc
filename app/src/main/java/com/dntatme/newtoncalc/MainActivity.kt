package com.dntatme.newtoncalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://newton.now.sh/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newton = retrofit.create(NewtonService::class.java)
    var operation = "simplify"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View) {
        operation = (view as Button).text.toString()
        if (operation == "log" || operation == "tangent") {
            text3.visibility = View.VISIBLE
            text2.visibility = View.INVISIBLE
            text2.text.clear()
        } else if (operation == "area") {
            text2.visibility = View.VISIBLE
            text3.visibility = View.VISIBLE
        } else {
            text2.visibility = View.INVISIBLE
            text2.text.clear()
            text3.visibility = View.INVISIBLE
            text3.text.clear()
        }
    }

    fun String.spaced(separator: String): String? {
        if (this != "")
            return separator + this
        return this
    }

    fun onSendButtonClick(view: View) {
        val expression = StringBuilder()
            .append( text1.text.toString() )
            .append( text2.text.toString().spaced(":") )
            .append( text3.text.toString().spaced("|") ).toString()
        Log.d("ABC", "${"$operation $expression"}")

        val call = newton.getResult(operation,expression)

        call.enqueue(object : Callback<Newton> {
            override fun onFailure(call: Call<Newton>, t: Throwable) {
                Log.e("FAILURE: ", " request has failed")
            }

            override fun onResponse(call: Call<Newton>, response: Response<Newton>) {
                val body = response.body()
                result.text = body!!.result?.toString()
            }
        })

    }

}
