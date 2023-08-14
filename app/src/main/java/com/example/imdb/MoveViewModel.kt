package com.example.imdb
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)
    private val _movieLiveData = MutableLiveData<JSONObject>()
    val movieLiveData: LiveData<JSONObject> = _movieLiveData
    private var dataFetched = false

    fun fetchMovie(title : String) {
        var url = "https://www.omdbapi.com"
        val queryParams = "?apikey=5b0f5a9d&t=$title"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            url + queryParams,
            null,
            { response ->
                // Handle the JSON response here
                _movieLiveData.value = response
                dataFetched=true
            },
            { error ->
                // Handle the error here
                println("Error: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun isDataFetched(): Boolean {
        return dataFetched
    }
}