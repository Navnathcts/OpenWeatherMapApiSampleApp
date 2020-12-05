package com.poc.openweatherapisample.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poc.openweatherapisample.model.CityDetailModel


object SharedPrefUtility {
    val PREFS_FILE_NAME = "SettingSharedPref"
    fun getPrefManager(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveBooleanValue(context: Context, key: String, value: Boolean) {
        getPrefManager(context)?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean {
        return getPrefManager(context)?.getBoolean(key, false) ?: false
    }

    fun saveStringValue(context: Context, key: String?, value: String) {
        getPrefManager(context)?.edit()?.putString(key, value)?.apply()
    }

    fun getStringValue(context: Context, key: String): String? {
        return getPrefManager(context)?.getString(key, null)
    }

    fun saveHashMap(context: Context,key: String?, map: MutableMap<String?, CityDetailModel?>?) {
        val editor = getPrefManager(context)?.edit()
        val gson = Gson()
        getHashMap(context, key)?.let { map?.putAll(it) }
        val json = gson.toJson(map)
        editor?.putString(key, json)
        editor?.apply()
    }


    fun getHashMap(context: Context,key: String?): HashMap<String?, CityDetailModel?>? {
        val prefs = getPrefManager(context)
        val gson = Gson()
        val json = prefs?.getString(key, mutableMapOf<String?,CityDetailModel?>().toString())
        val type = object : TypeToken<HashMap<String?, CityDetailModel?>?>() {}.type
        return gson.fromJson(json, type)
    }
}

