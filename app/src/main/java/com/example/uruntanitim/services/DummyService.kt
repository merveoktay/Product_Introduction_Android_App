package com.example.uruntanitim.services

import android.text.Editable
import com.example.uruntanitim.models.Products
import com.example.uruntanitim.models.User
import com.example.uruntanitim.models.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyService {
    @POST("/auth/login")
    fun login( @Body User: User): Call<UserData>

    @GET("products?limit=10")
    fun getProducts(): Call<Products>

   @GET("products/search")
    fun search(@Query ("q") query: Editable):Call<Products>
}