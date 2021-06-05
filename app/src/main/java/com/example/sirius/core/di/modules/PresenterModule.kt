package com.example.sirius.core.di.modules

import androidx.lifecycle.ViewModel
import com.example.sirius.core.di.factory.ViewModelKey
import com.example.sirius.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresenterModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}