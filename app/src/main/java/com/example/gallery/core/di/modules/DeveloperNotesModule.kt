package com.example.gallery.core.di.modules

import com.example.gallery.domain.model.handler.ResponseHandler
import com.example.gallery.domain.model.handler.impl.ResponseHandlerImpl
import com.example.gallery.domain.model.usecase.impl.GetBestNoteUseCaseImpl
import com.example.gallery.domain.model.usecase.impl.GetHotNoteUseCaseImpl
import com.example.gallery.domain.model.usecase.impl.GetLatestNoteUseCaseImpl
import com.example.gallery.domain.model.usecase.interfaces.GetBestNoteUseCase
import com.example.gallery.domain.model.usecase.interfaces.GetHotNoteUseCase
import com.example.gallery.domain.model.usecase.interfaces.GetLatestNoteUseCase
import com.example.gallery.domain.repository.DeveloperNoteRepository
import com.example.gallery.domain.repository.impl.DeveloperRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DeveloperNotesModule {
    @Binds
    @Singleton
    fun bindRepository(repositoryImpl: DeveloperRepositoryImpl): DeveloperNoteRepository

    @Binds
    @Singleton
    fun bindHandler(handlerImpl: ResponseHandlerImpl): ResponseHandler

    @Binds
    @Singleton
    fun bindGetLatestNoteUseCase(useCaseImpl: GetLatestNoteUseCaseImpl): GetLatestNoteUseCase

    @Binds
    @Singleton
    fun bindGetBestNoteUseCase(useCaseImpl: GetBestNoteUseCaseImpl): GetBestNoteUseCase

    @Binds
    @Singleton
    fun bindGetHotNoteUseCase(useCaseImpl: GetHotNoteUseCaseImpl): GetHotNoteUseCase
}