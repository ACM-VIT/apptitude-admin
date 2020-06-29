package com.benrostudios.apptitudeadmin.internal

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsUtils(
    private val context: Context
) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Data", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor

    init {
        editor = sharedPreferences.edit()
    }

    companion object {
        const val SHARED_PREFERENCE_MOBILE = "mobile"
    }

    fun saveMobile(mobile: String) = editor.putString(SHARED_PREFERENCE_MOBILE, mobile).commit()
    fun retrieveMobile(): String? = sharedPreferences.getString(SHARED_PREFERENCE_MOBILE, "")
}