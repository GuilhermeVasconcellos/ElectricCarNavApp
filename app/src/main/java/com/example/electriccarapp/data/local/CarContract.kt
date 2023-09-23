package com.example.electriccarapp.data.local

import android.provider.BaseColumns

object CarContract {

    const val DATABASE_NAME = "db_cars"

    object CarEntry : BaseColumns {

        const val TABLE_NAME = "cars"

        //        const val COLUMN_NAME_ID = "id"   // o SQLite já provê o id
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POWER = "power"
        const val COLUMN_NAME_RECHARGE = "recharge"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
        const val COLUMN_NAME_IS_FAVORITE = "favorite"
    }

    const val TABLE_CAR =
        "CREATE TABLE IF NOT EXISTS ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_PRICE} TEXT," +
                "${CarEntry.COLUMN_NAME_BATTERY} TEXT," +
                "${CarEntry.COLUMN_NAME_POWER} TEXT," +
                "${CarEntry.COLUMN_NAME_RECHARGE} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT," +
                "${CarEntry.COLUMN_NAME_IS_FAVORITE} INTEGER)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"

}

