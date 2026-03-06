package fr.isen.sophie.thegreatestcocktailapp

import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}