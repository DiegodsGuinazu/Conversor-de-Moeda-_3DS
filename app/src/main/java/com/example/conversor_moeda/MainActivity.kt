package com.example.conversor_moeda

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.conversor_moeda.api.ClientApi
import com.example.conversor_moeda.model.FinanceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    var cotacaoDollar : Double = 0.0
    var cotacaoEuro : Double = 0.0
    var cotacaoPeso : Double = 0.0
    var cotacaoLibra : Double = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
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

        val tempo = findViewById<TextView>(R.id.textTempo)

        tempo.text = saudacao()

        val btnConverte = findViewById<Button>(R.id.btnConverter)
        btnConverte.setOnClickListener {
            val valor = findViewById<EditText>(R.id.txtValor).text.toString().toDouble()
            val itemSelecionado = spMoeda.selectedItem.toString()
            val valorCotacao = when(itemSelecionado){
                "Dollar" -> valor * cotacaoDollar
                "Euro" -> valor * cotacaoEuro
                "Peso Argentino" -> valor * cotacaoPeso
                "Libra" -> valor * cotacaoLibra
                else -> {
                    0.0
                }
            }
            val resultado = findViewById<TextView>(R.id.textViewResultado)
            resultado.text = "Valor em reais %.2f".format(valorCotacao)
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
                p1.printStackTrace()
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun saudacao():String {
        val hora = LocalTime.now().hour
        val mensagem = when {
            hora < 12 -> "Bom dia!"
            hora < 18 -> "Boa tarde!"
            else ->{
                "Boa noite!"

            }
        }
        return mensagem
    }
}