package com.example.conversor_moeda

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.conversor_moeda.api.ClientApi
import com.example.conversor_moeda.model.FinanceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var cotacaoDollar : Double = 0.0
    var cotacaoEuro : Double = 0.0
    var cotacaoPeso : Double = 0.0
    var cotacaoLibra : Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val moedas = arrayOf("Dollar","Euro","Libra","Peso Argentino")
        val spMoeda = findViewById<Spinner>(R.id.spMoedas)

        val moedasAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,moedas)

        spMoeda.adapter = moedasAdapter
        carregarCotacoes()

        val btnConverte = findViewById<Button>(R.id.btnConverter)
        btnConverte.setOnClickListener {

        }
    }

    private fun carregarCotacoes() {
        ClientApi.api.getCotacoes().enqueue(object : Callback<FinanceResponse> {
            override fun onResponse(
                p0: Call<FinanceResponse?>,
                response: Response<FinanceResponse?>
            ) {
                val moedas = response.body()?.results?.currencies
                cotacaoDollar = moedas?.USD?.buy ?: 0.0
                cotacaoEuro = moedas?.EUR?.buy ?: 0.0
                cotacaoPeso = moedas?.ARS?.buy ?: 0.0
                cotacaoLibra = moedas?.GBP?.buy ?: 0.0
            }

            override fun onFailure(
                p0: Call<FinanceResponse?>,
                p1: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}