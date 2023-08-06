package com.sublimetech.supervisor.domain.useCases.forms

data class FormsUseCases(
    val createFormUseCase: CreateFormUseCase,
    val getFormById: GetFormByIdUseCase,
    val checkFormExist: CheckFormExist,

    val updateFormUseCase: UpdateFormUseCase,
    val getFormsUseCase: GetFormsUseCase,
    val getFormsSnapshotUseCase: GetFormsSnapshotUseCase
)


//Sacar offline forms de townDetails para ver que onda ya que fb si actualiza en local con snapshot listener no se que onda looll