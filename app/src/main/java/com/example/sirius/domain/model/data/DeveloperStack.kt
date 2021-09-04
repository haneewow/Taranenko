package com.example.sirius.domain.model.data

data class DeveloperStack(
    val top: Result<DeveloperNote?>,
    val bottom: Result<DeveloperNote?>
)