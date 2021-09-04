package com.example.gallery.core.di.modules

import com.example.gallery.ui.pages.best.BestFragment
import com.example.gallery.ui.pages.hot.HotFragment
import com.example.gallery.ui.pages.latest.LatestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeLatestFragment(): LatestFragment

    @ContributesAndroidInjector
    internal abstract fun contributeBestFragment(): BestFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHotFragment(): HotFragment
}