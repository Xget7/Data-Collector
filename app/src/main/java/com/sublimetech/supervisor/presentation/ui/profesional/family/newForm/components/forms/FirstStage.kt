package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.forms

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.data.model.family.FamilyRelationship
import com.sublimetech.supervisor.data.model.family.FamilyStudentDto
import com.sublimetech.supervisor.data.model.family.VisitDto
import com.sublimetech.supervisor.data.model.youtAndWoman.StudentDto
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.*
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components.custom.DevEvidenceAndAssistant
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.getCurrentDateTime
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.toString
import java.io.File
import java.util.UUID

@Composable
fun FirstStage(
    familyId: String,
    studentInfo: MutableState<FamilyStudentDto>,
    familyRelationship: MutableState<FamilyRelationship>,
    relativesInfo: MutableList<MutableState<FamilyMemberDto>>,
    visit: MutableState<VisitDto>,
    page: MutableState<Int>,
    direction: MutableState<Boolean>,
    attendancePeople: MutableList<StudentDto>,
    onSetRelatives: (Int) -> Unit,
    onSaveForm: () -> Unit
) {
    val relativesQuantity = remember {
        mutableStateOf(1)
    }

    val context = LocalContext.current
    val outputDirectory = File(context.filesDir, "")



    PageContent(
        changePage = { page.value = it },
        pageNumber = 0,
        right = direction.value,
        page = page.value,
        changeDirection = { direction.value = it },
        numeroDePaginas = relativesQuantity.value + 4
    ) { pageInfo ->
        StudentInfoComponent(
            pageInfo,
            studentInfo = studentInfo,
            saveInfoEstudiante = { studentInfo.value = it },
            relativesQuantity = {
                relativesQuantity.value = it
                onSetRelatives(it)

            }
        )
    }

    //for each relative make a list etc
    repeat(relativesQuantity.value) { index ->
        PageContent(
            changePage = { page.value = it },
            pageNumber = index + 1,
            right = direction.value,
            page = page.value,
            changeDirection = { direction.value = it },
            numeroDePaginas = relativesQuantity.value + 4
        ) { pageInfo ->

            RelativeInfo(
                pageInfo,
                infoFamiliar = relativesInfo[index],
                familyId,
                index
            )
        }
    }

    PageContent(
        changePage = { page.value = it },
        pageNumber = relativesQuantity.value + 1,
        right = direction.value,
        page = page.value,
        changeDirection = { direction.value = it },
        numeroDePaginas = relativesQuantity.value + 4
    ) { pageInfo ->
        AdditionalInfoComponent(
            pageInfo,
            familyRelationship = familyRelationship,
            saveAditionalInfo = { familyRelationship.value = it },
        )
    }

    PageContent(
        changePage = { page.value = it },
        pageNumber = relativesQuantity.value + 2,
        right = direction.value,
        page = page.value,
        changeDirection = { direction.value = it },
        numeroDePaginas = relativesQuantity.value + 4
    ) { pageInfo ->
        PrivacyPolicy(
            pageInfo,
            studentName = studentInfo.value.name,
            studentDocument = studentInfo.value.docNumber,
            familyName = relativesInfo.first().value.name,
            familyDocument = relativesInfo.first().value.documentNumber,
            signature = visit.value.guardianSignature
        ) {
            visit.value = visit.value.copy(guardianSignature = it)
        }
    }

    PageContent(
        changePage = { page.value = it },
        pageNumber = relativesQuantity.value + 3,
        right = direction.value,
        page = page.value,
        changeDirection = { direction.value = it },
        numeroDePaginas = relativesQuantity.value + 4
    ) { pageInfo ->

        DevEvidenceAndAssistant(
            assistanceHashMap = visit.value.assistance,
            photosMap = visit.value.developmentPhotos,
            outputDirectory = outputDirectory,
            onSaveForm = {
                visit.value = visit.value.copy(
                    id = UUID.randomUUID().toString(),
                    date = getCurrentDateTime().toString("yyyy/MM/dd"),
                    professionalSignature1 = it
                )
                onSaveForm()
            },
            pageInfo = pageInfo,
            attendancePeople = attendancePeople
        )


    }


}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun FirstFormPrev() {

//    FirstStage(
//        mutableStateOf(FamilyStudentDto()), mutableStateOf(FamilyRelationship()),
//        mutableStateOf(FamilyMemberDto()),
//        mutableStateOf(VisitDto()),
//        mutableStateOf(0),
//        mutableStateOf(0)
//    )
}
