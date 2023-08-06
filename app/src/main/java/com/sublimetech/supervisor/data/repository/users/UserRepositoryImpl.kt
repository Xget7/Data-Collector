package com.sublimetech.supervisor.data.repository.users

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.sublimetech.supervisor.data.model.user.UserDto
import com.sublimetech.supervisor.domain.repositories.users.UsersRepository
import com.sublimetech.supervisor.presentation.utils.FirebasePaths.USERS
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UsersRepository {


    override fun createUser(user: UserDto): Task<Void> {
        return db
            .collection(USERS)
            .document(user.id)
            .set(user)
    }

    override fun getUser(id: String): Task<DocumentSnapshot> {
        return db
            .collection(USERS)
            .document(id)
            .get()
    }

    override fun getUserReference(id: String): DocumentReference {
        return db
            .collection(USERS)
            .document(id)

    }

    override fun deleteUser(id: String): Task<Void> {
        return db
            .collection(USERS)
            .document(id)
            .delete()
    }

    override fun updateUser(userId: String, user: Map<String, Any>): Task<Void> {
        return db
            .collection(USERS)
            .document(userId)
            .update(user)
    }


}