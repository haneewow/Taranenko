package com.example.sirius.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sirius.domain.model.SessionInfo
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.DeveloperStack
import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.usecase.interfaces.GetNextNoteUseCase
import com.example.sirius.domain.model.usecase.interfaces.GetNoteUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val getNextNoteUseCase: GetNextNoteUseCase,
    private val sessionInfo: SessionInfo
) : ViewModel() {
    private val _notes = MutableLiveData<DeveloperStack?>()
    val notes: LiveData<DeveloperStack?> get() = _notes

    private val _buttonEnabled = MutableLiveData(false)
    val buttonEnabled: LiveData<Boolean> get() = _buttonEnabled

    private var number: Int
        get() = sessionInfo.pageNumber
        set(value) {
            sessionInfo.pageNumber = value
        }

    init {
        loadNotes()
    }

    fun loadNotes(isNextPage: Boolean? = null) = viewModelScope.launch(IO) {
        var numberPage = getCurrentPage(isNextPage)
        getNoteUseCase(numberPage).combine(getNextNoteUseCase(++numberPage)) { first, second ->
            DeveloperStack(top = first, bottom = second)
        }.collect {
            _notes.postValue(it)
            if (numberPage == FIRST_PAGE) _buttonEnabled.postValue(false)
            else _buttonEnabled.postValue(true)
        }
    }

    fun clearData() {
        viewModelScope.launch(IO) {
            sessionInfo.logout()
            loadNotes()
        }
    }

    private fun getCurrentPage(isNextPage: Boolean?): Int {
        isNextPage ?: return number
        return if (isNextPage) ++number else --number
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}