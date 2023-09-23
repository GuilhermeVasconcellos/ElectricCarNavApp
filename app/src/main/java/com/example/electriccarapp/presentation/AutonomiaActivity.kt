package com.example.electriccarapp.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electriccarapp.R
import com.example.electriccarapp.databinding.ActivityAutonomiaBinding

class AutonomiaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAutonomiaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        val previousCalcValue = getSharedPref()
        binding.tvResultado.text = previousCalcValue.toString()

    }

    private fun setupListeners() {
        binding.btnCalcular.setOnClickListener {
            calcularAutonomia()
        }
        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    private fun calcularAutonomia() {
        val cargaUsada = binding.etKwh.text.toString()
        val distanciaPercorrida = binding.etKmPercorrido.text.toString()
        val autonomia = distanciaPercorrida.toFloat() / cargaUsada.toFloat()
        binding.tvResultado.text = "${autonomia.toString()}"
        saveSharedPref(autonomia)
    }

    fun saveSharedPref(resultado: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.shared_calc), resultado)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.shared_calc), 0f)
    }
}