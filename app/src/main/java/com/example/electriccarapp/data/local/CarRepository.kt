package com.example.electriccarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_BATTERY
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_IS_FAVORITE
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_POWER
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_PRICE
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_RECHARGE
import com.example.electriccarapp.data.local.CarContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.electriccarapp.domain.Car
import java.lang.Boolean.getBoolean
import java.lang.Exception

class CarRepository(private val context: Context) {

    fun save(car: Car): Any? {

        try{
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(CarContract.CarEntry.COLUMN_NAME_PRICE, car.preco)
                put(CarContract.CarEntry.COLUMN_NAME_BATTERY, car.bateria)
                put(CarContract.CarEntry.COLUMN_NAME_POWER, car.potencia)
                put(CarContract.CarEntry.COLUMN_NAME_RECHARGE, car.recarga)
                put(CarContract.CarEntry.COLUMN_NAME_URL_PHOTO, car.urlPhoto)
                put(CarContract.CarEntry.COLUMN_NAME_IS_FAVORITE, car.isFavorite)
            }

            return db?.insert(CarContract.CarEntry.TABLE_NAME, null, values)

        }catch (e: Exception) {
            e.message?.let {
                Log.e("Erro", it)
            }
        }
        return 0
    }

    fun update(car: Car): Any? {

        try{
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(CarContract.CarEntry.COLUMN_NAME_IS_FAVORITE, car.isFavorite)
            }

            val sql = "$COLUMN_NAME_IS_FAVORITE == ?"
            val finalValue = arrayOf(car.isFavorite.toString())

            return db?.update(CarContract.CarEntry.TABLE_NAME, values, sql, finalValue)

        }catch (e: Exception) {
            e.message?.let {
                Log.e("Erro", it)
            }
        }
        return 0
    }

    fun findCarById(id: Int): Car {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        // lista de colunas a serem exibidas no resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO,
            COLUMN_NAME_IS_FAVORITE
        )

        val filter = "${BaseColumns._ID} = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )

        var myCar =Car(0, "", "", "", "", "", 0)

        val itemCar = mutableListOf<Car>()
        with(cursor) {
            while(moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                Log.i("sqlite", "id -> ${itemId.toString()}")
                val itemPrice = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.i("sqlite", "preço -> $itemPrice")
                val itemBattery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.i("sqlite", "bateria -> $itemBattery")
                val itemPower = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER))
                Log.i("sqlite", "potência -> $itemPower")
                val itemRecharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.i("sqlite", "recarga -> $itemRecharge")
                val itemUrlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.i("sqlite", "url -> $itemUrlPhoto")
                val itemFavorite = getInt(getColumnIndexOrThrow(COLUMN_NAME_IS_FAVORITE))
                Log.i("sqlite", "favorito -> ${itemFavorite.toString()}")
                myCar = Car(itemId, itemPrice, itemBattery, itemPower, itemRecharge, itemUrlPhoto, itemFavorite)
            }
        }
        cursor?.close()
        return myCar
    }

    fun findAllCars(): List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        // lista de colunas a serem exibidas no resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO,
            COLUMN_NAME_IS_FAVORITE
        )

        val cursor = db.query(
            CarContract.CarEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val listCar = mutableListOf<Car>()
        with(cursor) {
            while(moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                Log.i("sqlite", "id -> ${itemId.toString()}")
                val itemPrice = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.i("sqlite", "preço -> $itemPrice")
                val itemBattery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.i("sqlite", "bateria -> $itemBattery")
                val itemPower = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER))
                Log.i("sqlite", "potência -> $itemPower")
                val itemRecharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.i("sqlite", "recarga -> $itemRecharge")
                val itemUrlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.i("sqlite", "url -> $itemUrlPhoto")
                val itemFavorite = getInt(getColumnIndexOrThrow(COLUMN_NAME_IS_FAVORITE))
                Log.i("sqlite", "favorito -> ${itemFavorite.toString()}")
                listCar.add(
                    Car(itemId, itemPrice, itemBattery, itemPower, itemRecharge, itemUrlPhoto, itemFavorite)
                )
            }
        }
        cursor?.close()
        return listCar
    }

    fun findFavoriteCars(): List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        // lista de colunas a serem exibidas no resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO,
            COLUMN_NAME_IS_FAVORITE
        )

        val filter = "${COLUMN_NAME_IS_FAVORITE} = 1"

        val cursor = db.query(
            CarContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            null,
            null,
            null,
            null
        )

        var myCar =Car(0, "", "", "", "", "", 0)

        val favoriteListCar = mutableListOf<Car>()
        with(cursor) {
            while(moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                Log.i("sqlite", "id -> ${itemId.toString()}")
                val itemPrice = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.i("sqlite", "preço -> $itemPrice")
                val itemBattery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.i("sqlite", "bateria -> $itemBattery")
                val itemPower = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER))
                Log.i("sqlite", "potência -> $itemPower")
                val itemRecharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.i("sqlite", "recarga -> $itemRecharge")
                val itemUrlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.i("sqlite", "url -> $itemUrlPhoto")
                val itemFavorite = getInt(getColumnIndexOrThrow(COLUMN_NAME_IS_FAVORITE))
                Log.i("sqlite", "favorito -> ${itemFavorite.toString()}")
                favoriteListCar.add(
                    Car(itemId, itemPrice, itemBattery, itemPower, itemRecharge, itemUrlPhoto, itemFavorite)
                )
            }
        }
        cursor?.close()
        return favoriteListCar
    }
}