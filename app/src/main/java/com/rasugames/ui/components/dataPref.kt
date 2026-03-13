package com.rasugames.ui.components

import android.app.Activity
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rasugames.ui.screens.has
import com.rasugames.utils.NotFoundException
import com.rasugames.utils.core.IpScore
import kotlinx.coroutines.flow.first

private val Context.dataPref by preferencesDataStore(name = "zz")
object Save {
    private val KEY_STRING = stringPreferencesKey("vv")

    suspend fun saveStringOnce(context: Context, value: String) {
        val dataStore = context.dataPref

        val existing = dataStore.data.first()[KEY_STRING]
        if (existing != null) {
            throw IllegalStateException("Value already saved: $existing")
        }

        IpScore.invoke(context as Activity)
        has()
        dataStore.edit { prefs ->
            prefs[KEY_STRING] = value
        }
    }

    suspend fun getUrl(context: Context): String {
        val dataStore = context.dataPref
        return dataStore.data.first()[KEY_STRING] ?: throw NotFoundException()
    }
}
