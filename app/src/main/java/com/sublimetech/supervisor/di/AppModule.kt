package com.sublimetech.supervisor.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sublimetech.supervisor.data.local.dao.FormsDao
import com.sublimetech.supervisor.data.local.dao.VisitDao
import com.sublimetech.supervisor.data.repository.admin.ProjectRepositoryImpl
import com.sublimetech.supervisor.data.repository.images.FirebaseStorageRepositoryImpl
import com.sublimetech.supervisor.data.repository.images.OfflineImageRepositoryImpl
import com.sublimetech.supervisor.data.repository.network.ConnectivityImpl
import com.sublimetech.supervisor.data.repository.projects.FamilyLocalRepositoryImpl
import com.sublimetech.supervisor.data.repository.projects.FamilyRepositoryImpl
import com.sublimetech.supervisor.data.repository.projects.FormRepositoryImpl
import com.sublimetech.supervisor.data.repository.projects.GroupRepositoryImpl
import com.sublimetech.supervisor.data.repository.users.UserRepositoryImpl
import com.sublimetech.supervisor.domain.providers.FilesProvider
import com.sublimetech.supervisor.domain.repositories.images.FirebaseStorageRepository
import com.sublimetech.supervisor.domain.repositories.images.OfflineFormsRepository
import com.sublimetech.supervisor.domain.repositories.network.ConnectivityInterface
import com.sublimetech.supervisor.domain.repositories.professional.FamilyLocalRepository
import com.sublimetech.supervisor.domain.repositories.professional.FamilyRepository
import com.sublimetech.supervisor.domain.repositories.professional.FormRepository
import com.sublimetech.supervisor.domain.repositories.professional.GroupRepository
import com.sublimetech.supervisor.domain.repositories.project.ProjectRepository
import com.sublimetech.supervisor.domain.repositories.users.UsersRepository
import com.sublimetech.supervisor.domain.useCases.family.*
import com.sublimetech.supervisor.domain.useCases.forms.*
import com.sublimetech.supervisor.domain.useCases.groups.CreateGroupUseCase
import com.sublimetech.supervisor.domain.useCases.groups.GetGroupListUseCase
import com.sublimetech.supervisor.domain.useCases.groups.GetGroupUseCase
import com.sublimetech.supervisor.domain.useCases.groups.GroupsUseCases
import com.sublimetech.supervisor.domain.useCases.projects.*
import com.sublimetech.supervisor.domain.useCases.storage.FirebaseStorageUseCases
import com.sublimetech.supervisor.domain.useCases.storage.files.DownloadFileUseCase
import com.sublimetech.supervisor.domain.useCases.storage.images.UploadImageUseCase
import com.sublimetech.supervisor.domain.useCases.user.*
import com.sublimetech.supervisor.presentation.utils.LocationUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UsersRepository {
        return UserRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFamilyRepository(fb: FirebaseFirestore): FamilyRepository {
        return FamilyRepositoryImpl(fb)
    }

    @Provides
    @Singleton
    fun provideProjectRepository(fb: FirebaseFirestore): ProjectRepository {
        return ProjectRepositoryImpl(fb)
    }

    @Provides
    @Singleton
    fun provideFormRepository(fb: FirebaseFirestore): FormRepository {
        return FormRepositoryImpl(fb)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(fb: FirebaseFirestore): GroupRepository {
        return GroupRepositoryImpl(fb)
    }


    @Provides
    @Singleton
    fun provideNetworkRepository(@ApplicationContext appContext: Context): ConnectivityInterface {
        return ConnectivityImpl(appContext)
    }


    @Provides
    @Singleton
    fun provideFirebaseStorageRepository(storage: FirebaseStorage): FirebaseStorageRepository {
        return FirebaseStorageRepositoryImpl(storage)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("local", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOfflineImageRepository(
        dao: FormsDao,
        @ApplicationContext c: Context
    ): OfflineFormsRepository {
        return OfflineImageRepositoryImpl(dao, c)
    }

    @Provides
    @Singleton
    fun provideOVisitsRepository(dao: VisitDao): FamilyLocalRepository {
        return FamilyLocalRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUsersUseCases(userRepo: UsersRepository): UserUseCases {
        return UserUseCases(
            getUserTypeUseCase = GetUserTypeUseCase(userRepo),
            getUserUseCase = GetUserUseCase(userRepo),
            createUserUseCase = CreateUserUseCase(userRepo),
            getUserForSignatureUseCase = GetUserForSignatureUseCase(userRepo),
            getUserSnapshotUseCase = GetUserSnapshotUseCase(userRepo)
        )
    }

    @Provides
    @Singleton
    fun provideFamilyUseCases(repo: FamilyRepository): FamiliesUseCases {
        return FamiliesUseCases(
            createFamilyUseCase = CreateFamilyUseCase(repo),
            updateFamilyUseCase = UpdateFamilyUseCase(repo),
            createVisitUseCase = CreateVisitUseCase(repo),
            getFamiliesUseCase = GetFamiliesUseCase(repo),
            getFamilyUseCase = GetFamilyUseCase(repo),
            checkFamilyExist = CheckFamilyExist(repo)

        )
    }

    @Provides
    @Singleton
    fun provideProjectUseCases(repo: ProjectRepository): ProjectUseCases {
        return ProjectUseCases(
            getProjectUseCase = GetProjectUseCase(repo),
            createProjectUseCase = CreateProjectUseCase(repo),
            getProjectByParticipant = GetProjectByParticipant(repo),
            getTownsById = GetTownsById(repo),
            getTownById = GetTownById(repo)
        )
    }

    @Provides
    @Singleton
    fun provideGroupsUseCases(repo: GroupRepository): GroupsUseCases {
        return GroupsUseCases(
            getGroupUseCase = GetGroupUseCase(repo),
            createGroupUseCase = CreateGroupUseCase(repo),
            getGroupListUseCase = GetGroupListUseCase(repo),
        )
    }

    @Provides
    @Singleton
    fun provideFormsUseCases(repo: FormRepository): FormsUseCases {
        return FormsUseCases(
            createFormUseCase = CreateFormUseCase(repo),
            getFormById = GetFormByIdUseCase(repo),
            updateFormUseCase = UpdateFormUseCase(repo),
            getFormsUseCase = GetFormsUseCase(repo),
            getFormsSnapshotUseCase = GetFormsSnapshotUseCase(repo),
            checkFormExist = CheckFormExist(repo)
        )
    }


    @Provides
    @Singleton
    fun provideStorageUseCases(
        repo: FirebaseStorageRepository,
        offlineFormsRepository: OfflineFormsRepository
    ): FirebaseStorageUseCases {
        return FirebaseStorageUseCases(
            downloadFileUseCase = DownloadFileUseCase(repo),
            uploadImageUseCase = UploadImageUseCase(repo, offlineFormsRepository)
        )
    }


    @Provides
    @Singleton
    fun provideFilesProvider(@ApplicationContext context: Context): FilesProvider {
        return FilesProvider(context)
    }


    @Provides
    @Singleton
    fun provideLocationUtils(appModule: Application): LocationUtils {
        return LocationUtils(appModule)
    }


}