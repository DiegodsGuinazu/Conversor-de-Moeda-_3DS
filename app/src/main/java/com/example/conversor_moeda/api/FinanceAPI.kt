package com.example.conversor_moeda.api

import rettrofit2.Call
import retrofit2.http.GET

interface FinanceAPI {
    @GET("finance?key=d18b57f7")
    fun getCotacoes() : Call<FinanceResponse>
}