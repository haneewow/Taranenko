package com.example.gallery.ui.pages.hot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gallery.domain.model.SessionInfo
import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.Result
import com.example.gallery.domain.model.usecase.interfaces.GetHotNoteUseCase
import com.example.gallery.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotViewModel @Inject constructor(
    private val getHotUseCase: GetHotNoteUseCase,
    private val sessionInfo: SessionInfo
) : BaseViewModel(sessionInfo) {
    private val _notes = MutableLiveData<Result<DeveloperNote?>>()
    val notes: LiveData<Result<DeveloperNote?>> get() = _notes
    private val _buttonEnabled = MutableLiveData(false)
    val buttonEnabled: LiveData<Boolean> get() = _buttonEnabled

    init { loadNotes() }

    override var pageNumber: Int
        get() = sessionInfo.pageNumberHot
        set(value) {
            sessionInfo.pageNumberHot = value
        }

    override fun loadNotes(isNextPage: Boolean?) {
        viewModelScope.launch(IO) {
            val numberPage = getCurrentPage(isNextPage)
            getHotUseCase(numberPage)
                .collect {
                    _notes.postValue(it)
                    if (numberPage == FIRST_PAGE) _buttonEnabled.postValue(false)
                    else _buttonEnabled.postValue(true)
                }
        }
    }
}