package com.example.electriccarapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.data.CarFactory.carList
import com.example.electriccarapp.data.local.CarRepository
import com.example.electriccarapp.domain.Car
import com.example.electriccarapp.presentation.adapter.CarAdapter

class FavoriteFragment : Fragment() {

    lateinit var rvFavoriteCars: RecyclerView
    lateinit var favoriteCarList: List<Car>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favoriteCarList = CarRepository(requireContext()).findFavoriteCars()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavoriteCars = view.findViewById(R.id.rv_favorite_cars)
        setupFavoriteList(favoriteCarList)
    }

    fun setupFavoriteList(favoriteCarList: List<Car>) {
        // mostrando a lista dos carros favoritos
        val carAdapter = CarAdapter(favoriteCarList)
        rvFavoriteCars.apply {
            adapter = carAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }
}