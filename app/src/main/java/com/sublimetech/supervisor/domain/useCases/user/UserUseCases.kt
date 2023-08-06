package com.sublimetech.supervisor.domain.useCases.user

data class UserUseCases(
    val getUserTypeUseCase: GetUserTypeUseCase,
    val getUserUseCase: GetUserUseCase,
    val getUserSnapshotUseCase: GetUserSnapshotUseCase,
    val createUserUseCase: CreateUserUseCase,
    val getUserForSignatureUseCase: GetUserForSignatureUseCase
)
