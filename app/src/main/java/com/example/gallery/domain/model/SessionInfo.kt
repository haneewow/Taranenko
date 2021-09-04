package com.example.gallery.domain.model

import android.content.Context
import androidx.core.content.edit
import com.example.gallery.core.db.AppDatabase
import com.example.gallery.ui.adapter.Category
import javax.inject.Inject

class SessionInfo @Inject constructor(
    context: Context,
    private val database: AppDatabase
) {
    private val sessionInfo = context.getSharedPreferences(NUMBER_PREFS_KEY, Context.MODE_PRIVATE)

    var pageNumberLatest: Int
        get() = sessionInfo.getInt(NUMBER_PAGE_LATEST_KEY, FIRST_PAGE)
        set(value) = sessionInfo.edit { putInt(NUMBER_PAGE_LATEST_KEY, value) }

    var pageNumberBest: Int
        get() = sessionInfo.getInt(NUMBER_PAGE_BEST_KEY, FIRST_PAGE)
        set(value) = sessionInfo.edit { putInt(NUMBER_PAGE_BEST_KEY, value) }

    var pageNumberHot: Int
        get() = sessionInfo.getInt(NUMBER_PAGE_HOT_KEY, FIRST_PAGE)
        set(value) = sessionInfo.edit { putInt(NUMBER_PAGE_HOT_KEY, value) }

    fun сlearData(category: Category) {
        when(category) {
            Category.LATEST -> pageNumberLatest = FIRST_PAGE
            Category.HOT -> pageNumberHot = FIRST_PAGE
            Category.TOP -> pageNumberBest = FIRST_PAGE
        }

        database.appDao().deleteByCategory(category)
    }

    companion object {
        private const val NUMBER_PREFS_KEY = "info_prefs"
        private const val NUMBER_PAGE_LATEST_KEY = "number_page_latest"
        private const val NUMBER_PAGE_BEST_KEY = "number_page_best"
        private const val NUMBER_PAGE_HOT_KEY = "number_page_hot"
        private const val FIRST_PAGE = 1
    }
}