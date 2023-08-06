package com.sublimetech.supervisor.domain.useCases.family

data class FamiliesUseCases(
    val createFamilyUseCase: CreateFamilyUseCase,
    val updateFamilyUseCase: UpdateFamilyUseCase,
    val createVisitUseCase: CreateVisitUseCase,
    val checkFamilyExist: CheckFamilyExist,
    val getFamilyUseCase: GetFamilyUseCase,
    val getFamiliesUseCase: GetFamiliesUseCase
)
