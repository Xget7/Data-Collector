package com.sublimetech.supervisor.domain.useCases.projects

data class ProjectUseCases(
    val getProjectUseCase: GetProjectUseCase,
    val getProjectByParticipant: GetProjectByParticipant,
    val getTownsById: GetTownsById,
    val getTownById: GetTownById,
    val createProjectUseCase: CreateProjectUseCase
)