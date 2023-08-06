package com.sublimetech.supervisor.domain.useCases.groups

data class GroupsUseCases(
    val getGroupUseCase: GetGroupUseCase,
    val createGroupUseCase: CreateGroupUseCase,
    val getGroupListUseCase: GetGroupListUseCase,

    )
