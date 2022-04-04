package com.example.pandemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.pandemia.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            searchPandemicData()
        }
    }

    fun getRetrofit():Retrofit{
        return  Retrofit
            .Builder()
            .baseUrl("https://disease.sh/v3/covid-19/countries/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun searchPandemicData(){
        CoroutineScope(Dispatchers.Main).launch{
            try {
                val txtFilter: String = binding.etCountryName.text.toString()
                val call = getRetrofit().create(ApiService::class.java).getPandemicData(txtFilter)
                if (call.isSuccessful){
                    val country:String = call.body()?.country.toString()
                    val population:String = call.body()?.population.toString()
                    val cases:String = call.body()?.cases.toString()
                    val recovered:String = call.body()?.recovered.toString()

                    binding.TvCountry.text = country
                    binding.TvPopulation.text = population
                    binding.TvCases.text = cases
                    binding.TvRecovered.text = recovered
                }
            }catch (ex: Exception){
                val msg = Toast.makeText(this@MainActivity, "Error de conexion", Toast.LENGTH_LONG)
                msg.setGravity(Gravity.CENTER,0,0)
                msg.show()
            }
        }
    }
}