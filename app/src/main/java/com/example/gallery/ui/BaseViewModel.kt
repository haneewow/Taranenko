package com.example.gallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.domain.model.SessionInfo
import com.example.gallery.ui.adapter.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val sessionInfo: SessionInfo) : ViewModel() {
    abstract var pageNumber: Int
    abstract fun loadNotes(isNextPage: Boolean? = null)

    fun clearData(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            sessionInfo.сlearData(category)
            loadNotes()
        }
    }

    protected fun getCurrentPage(isNextPage: Boolean?): Int {
        isNextPage ?: return pageNumber
        return if (isNextPage) ++pageNumber else --pageNumber
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}