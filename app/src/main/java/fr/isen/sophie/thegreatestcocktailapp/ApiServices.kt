package fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {

    @GET("random.php")
    suspend fun getRandom(): DrinksResponse

    @GET("list.php")
    suspend fun getCategories(@Query("c") c: String = "list"): DrinksResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinksResponse

    @GET("lookup.php")
    suspend fun getDrinkDetail(@Query("i") id: String): DrinksResponse
}

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