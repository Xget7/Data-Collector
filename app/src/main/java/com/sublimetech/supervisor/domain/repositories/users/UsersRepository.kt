package com.sublimetech.supervisor.domain.repositories.users

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.sublimetech.supervisor.data.model.user.UserDto

interface UsersRepository {

    fun createUser(user: UserDto): Task<Void>

    fun getUser(id: String): Task<DocumentSnapshot>
    fun getUserReference(id: String): DocumentReference
    fun deleteUser(id: String): Task<Void>

    fun updateUser(userId: String, user: Map<String, Any>): Task<Void>
}