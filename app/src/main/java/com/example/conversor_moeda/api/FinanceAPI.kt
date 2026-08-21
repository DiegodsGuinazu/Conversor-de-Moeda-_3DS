package com.example.conversor_moeda.api

import com.example.conversor_moeda.model.FinanceResponse
import retrofit2.Call
import retrofit2.http.GET

interface FinanceAPI {
    @GET("finance?key=d18b57f7")
    fun getCotacoes() : Call<FinanceResponse>
}