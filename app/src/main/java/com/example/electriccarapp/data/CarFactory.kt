package com.example.electriccarapp.data

import com.example.electriccarapp.R
import com.example.electriccarapp.domain.Car
import com.example.electriccarapp.domain.Car1

object CarFactory {

    val carList = listOf<Car1>(
        Car1(1, "/res/drawable/electric_car.png", "Toyota Corolla", "R$300.000,00", "300 kWh", "200 cv", "30 min"),
        Car1(2, "/res/drawable/electric_car.png", "Honda Civic", "R$280.000,00", "300 kWh", "220 cv", "25 min"),
        Car1(3, "/res/drawable/electric_car.png", "Jeep Renegade", "R$260.000,00", "350 kWh", "280 cv", "40 min"),
        Car1(4, "/res/drawable/electric_car.png", "Honda HRV", "R$240.000,00", "320 kWh", "210 cv", "30 min"),
        Car1(5, "/res/drawable/electric_car.png", "Hiunday Creta", "R$200.000,00", "250 kWh", "190 cv", "20 min"),
        Car1(6, "/res/drawable/electric_car.png", "Nissan Kicks", "R$220.000,00", "255 kWh", "195 cv", "25 min"),
        Car1(7, "/res/drawable/electric_car.png", "Jeep Compass", "R$300.000,00", "400 kWh", "305 cv", "45 min"),
        Car1(8, "/res/drawable/electric_car.png", "Ford EcoSport", "R$190.000,00", "200 kWh", "185 cv", "20 min"),
        Car1(9, "/res/drawable/electric_car.png", "VW Nivus", "R$215.000,00", "250 kWh", "195 cv", "20 min"),
        Car1(10, "/res/drawable/electric_car.png", "VW T-Cross", "R$255.000,00", "290 kWh", "245 cv", "30 min"),
    )
}