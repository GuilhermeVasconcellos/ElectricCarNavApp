package com.example.electriccarapp.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.data.local.CarRepository
import com.example.electriccarapp.databinding.ItemCarBinding
import com.example.electriccarapp.domain.Car

class CarAdapter(val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    var carItemListener : (Car) -> Unit = {}

    inner class CarViewHolder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

        /*// usando o AsyncTask (Car1 model)
        fun bind(car: Car) {
            with(binding) {
                tvModelValue.text = car.model
                tvPriceValue.text = car.price
                tvBatteryValue.text = car.battery
                tvPowerValue.text = car.power
                tvRechargeValue.text = car.recharge
             }
        }*/

        // usando o Retrofit (Car model)
        fun bind(car: Car) {
            with(binding) {
                tvModelValue.text = "SUV"
                tvPriceValue.text = car.preco
                tvBatteryValue.text = car.bateria
                tvPowerValue.text = car.potencia
                tvRechargeValue.text = car.recarga

                ivFavorite.setOnClickListener {
                    carItemListener(car)
                    if(car.isFavorite == 0) {
                        car.isFavorite = 1
                    }else {
                        car.isFavorite = 0
                    }

                    if(car.isFavorite == 1) {
                        ivFavorite.setImageResource(android.R.drawable.btn_star_big_on)
                    }else {
                        ivFavorite.setImageResource(android.R.drawable.btn_star_big_off)
                    }
                }
            }
        }
    }

    // Cria um novo container de views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CarViewHolder(itemView)
    }

    // Pega o conteúdo do itemView e substitui pelas informações de uma lista
    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    // Pega a quantidade de itens da lista
    override fun getItemCount(): Int {
        return cars.size
    }

}