package com.example.githubapp.data.storage

import android.content.Context

private const val TOKEN_VALUE = "TOKEN_VALUE"
private const val TOKEN_KEY = "TOKEN_KEY"

class TokenSharedPref(
    context: Context
) {
    private val prefs = context.getSharedPreferences(TOKEN_VALUE, Context.MODE_PRIVATE)

    var localToken: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) = prefs.edit().putString(TOKEN_KEY, value).apply()
}