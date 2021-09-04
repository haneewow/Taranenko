package com.example.sirius.core.di.modules

import com.example.sirius.domain.model.handler.ResponseHandler
import com.example.sirius.domain.model.handler.impl.ResponseHandlerImpl
import com.example.sirius.domain.model.usecase.impl.GetNextNoteUseCaseImpl
import com.example.sirius.domain.model.usecase.impl.GetNoteUseCaseImpl
import com.example.sirius.domain.model.usecase.interfaces.GetNextNoteUseCase
import com.example.sirius.domain.model.usecase.interfaces.GetNoteUseCase
import com.example.sirius.domain.repository.DeveloperNoteRepository
import com.example.sirius.domain.repository.impl.DeveloperRepositoryImpl
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
    fun bindGetNoteUseCase(useCaseImpl: GetNoteUseCaseImpl): GetNoteUseCase

    @Binds
    @Singleton
    fun bindGetNextNoteUseCase(useCaseImpl: GetNextNoteUseCaseImpl): GetNextNoteUseCase

}