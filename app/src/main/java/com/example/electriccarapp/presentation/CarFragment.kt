package com.example.electriccarapp.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.data.CarsAPI
import com.example.electriccarapp.data.local.CarRepository
import com.example.electriccarapp.domain.Car
import com.example.electriccarapp.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep

class CarFragment : Fragment() {

    lateinit var rvCars: RecyclerView
    lateinit var fabAutonomy: FloatingActionButton
    lateinit var progress: ProgressBar
    lateinit var imageNoWifi: ImageView
    lateinit var textNoWifi: TextView

    // retrofit
    lateinit var carsAPI: CarsAPI

    val carModel = arrayOf("VW Nivus", "Nissan Kicks", "Jeep Renegade", "Hiunday Creta")
    private var carList: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        setupRetrofit()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCars = view.findViewById(R.id.rv_favorite_cars)
        fabAutonomy = view.findViewById(R.id.fab_autonomy)
        progress = view.findViewById(R.id.pb_loader)
        imageNoWifi = view.findViewById(R.id.iv_empty_state)
        textNoWifi = view.findViewById(R.id.tv_no_wifi)

        // pegando uma lista de carros mocados (CarFactory)
//        val carAdapter = CarAdapter(CarFactory.carList)


        fabAutonomy.setOnClickListener {
            startActivity(Intent(context, AutonomiaActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let { if(checkForInternet(it)) {
//                MyTask().execute("https://igorbag.github.io/cars-api/cars.json")  // forma mais nativa de chamar o serviço
                getAllCars()    // chamada do serviço uando Retrofit
                progress.visibility = View.VISIBLE
            }else {
                emptyState()
            }
        }
    }

    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsAPI = retrofit.create(CarsAPI::class.java)
    }

    fun getAllCars() {
        carsAPI.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful) {
                    imageNoWifi.isVisible = false
                    textNoWifi.isVisible = false
                    sleep(1000)
                    progress.isVisible = false

                    val carList = response.body()
                    carList?.let { setupList(it) }
                }else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun emptyState() {
        progress.isVisible = false  // progress.visibility = View.GONE
        rvCars.isVisible = false
        imageNoWifi.isVisible = true
        textNoWifi.isVisible = true
    }

    fun setupList(carList: List<Car>) {
        // pegando uma lista de carros de uma api (internet)
        val carAdapter = CarAdapter(carList)
        rvCars.apply {
            visibility = View.VISIBLE
            adapter = carAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        carAdapter.carItemListener = {car ->
            if(car.isFavorite == 1) {
                CarRepository(requireContext()).update(car)
            }
/*

            if(CarRepository(requireContext()).findCarById(car.id).id == 0) {
                val inserted = CarRepository(requireContext()).save(car)
                if (inserted != null) {
                    Toast.makeText(context, "Dados inseridos com sucesso", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(context, "Erro ao inserir os dados na tabela", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(context, "Erro ao inserir os dados na tabela: ID já existente!", Toast.LENGTH_LONG).show()
            }
*/

            // lista de carro por id
//            CarRepository(requireContext()).findCarById(car.id)

            // lista de todos os carros
            /*val listaTotal = CarRepository(requireContext()).findAllCars()
            listaTotal.forEach {
                Log.i("carros", "${it.id}) Preço: ${it.preco} / Potência: ${it.potencia}")
            }*/
        }
    }

    fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    // usar o Retrofit como abstração do AsyncTask :)
    /*
    inner class MyTask : AsyncTask<String, String, String>() {

        fun OnPreExecute() {
            super.onPreExecute()
            Log.i("car-api", "OnPreExecute: Iniciando...")

        }
        override fun doInBackground(vararg url: String?): String {

            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty("Accept", "application/json")

                val responseCode = urlConnection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK) {  // 200
                    var response = urlConnection.inputStream.bufferedReader().use {
                        it.readText()
                    }
                    publishProgress(response)
                }else {
                    Log.e("Erro", "Serviço indisponível no momento.", )
                }



            }catch (e: Exception) {
                e.printStackTrace()
                Log.e("Erro", "Erro ao conectar a fonte de dados")
            }finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                for(i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.i("car-api", "ID: $id")
                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.i("car-api", "Preço: $preco")
                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.i("car-api", "Bateria: $bateria")
                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.i("car-api", "Potência: $potencia")
                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    Log.i("car-api", "Recarga: $recarga")
                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.i("car-api", "URL Foto: $urlPhoto")

                    val model = Car1(
                        id.toInt(), carModel[i], preco, bateria, potencia, recarga, urlPhoto
                    )
                    Log.i("car-api", "Model: ${model.toString()}")
                    carList?.add(model)
                    Log.i("car-api", "Lista: ${carList?.size}")
                }
                imageNoWifi.visibility = View.GONE
                textNoWifi.visibility = View.GONE
                sleep(1000)
                progress.visibility = View.GONE

                carList?.let { setupList(it) }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }*/
}