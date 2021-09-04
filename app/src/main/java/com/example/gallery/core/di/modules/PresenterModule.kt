package com.example.gallery.core.di.modules

import androidx.lifecycle.ViewModel
import com.example.gallery.core.di.factory.ViewModelKey
import com.example.gallery.ui.pages.best.BestViewModel
import com.example.gallery.ui.pages.hot.HotViewModel
import com.example.gallery.ui.pages.latest.LatestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresenterModule {
    @Binds
    @IntoMap
    @ViewModelKey(LatestViewModel::class)
    abstract fun bindLatestViewModel(viewModel: LatestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BestViewModel::class)
    abstract fun bindBestViewModel(viewModel: BestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HotViewModel::class)
    abstract fun bindHotViewModel(viewModel: HotViewModel): ViewModel
}