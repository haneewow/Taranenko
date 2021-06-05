package com.example.sirius.domain.model

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.sirius.core.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionInfo @Inject constructor(
    context: Context,
    private val database: AppDatabase
) {
    private val sessionInfo = context.getSharedPreferences(NUMBER_PREFS_KEY, Context.MODE_PRIVATE)

    var pageNumber: Int
        get() = sessionInfo.getInt(NUMBER_PAGE_KEY, FIRST_PAGE)
        set(value) = sessionInfo.edit { putInt(NUMBER_PAGE_KEY, value) }

    fun logout() = CoroutineScope(IO).launch {
        sessionInfo.edit { clear() }
        database.clearAllTables()
    }

    companion object {
        private const val NUMBER_PREFS_KEY = "info_prefs"
        private const val NUMBER_PAGE_KEY = "number_page"
        private const val FIRST_PAGE = 1
    }
}