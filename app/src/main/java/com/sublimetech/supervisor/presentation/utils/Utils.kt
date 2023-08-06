package com.sublimetech.supervisor.presentation.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Patterns
import com.sublimetech.supervisor.domain.model.WomanForm.DeliverableTypesList
import java.io.File

object Utils {

    const val DEBUG = true
    const val ADMIN = "ADMIN"
    const val YOUTH = "YOUTH"
    const val WOMAN = "WOMAN"

    const val FAMILY = "FAMILY"

    val deliverableTypesListYOUTH = DeliverableTypesList(
        deliverableTypes = mutableListOf(
            "Instrumentos",
            "Materiales",
            "Alimentos",
            "Uniformes",
            "Equipos"
        ),
        materialList = mutableListOf(
            "Kit de apoyo pedagógico",
            "Kit de bioseguridad"
        ),
        instrumentList = mutableListOf(
            "Instrumento de percusión",
            "Instrumento de cuerda",
            "Instrumento de viento",
            "Acordeo",
            "Guacharaca"
        ),
        equipmentList = mutableListOf(
            "Consola de sonido",
            "Micrófono",
            "Parlante"
        ),
        uniformList = mutableListOf(
            "Camiseta", "Gorra", "Camiseta y gorra"
        ),
        foodList = mutableListOf(
            "Bebida", "Refrigerio", "Almuerzo", "Pasaboca", "Agua", "Café", "Café y agua"
        )
    )

    val deliverableTypesListWoman = DeliverableTypesList(
        deliverableTypes = mutableListOf(
            "Instrumentos",
            "Materiales",
            "Alimentos",
            "Uniformes",
        ),
        materialList = mutableListOf(
            "Kit de trabajo tejido",
            "Kit de bioseguridad",
            "Kit de trabajo de reciclables",
            "Kit de apoyo pedagógico"
        ),
        instrumentList = mutableListOf(
            "Instrumento de trabajo tejido",
            "Instrumento de trabajo de reciclables"
        ),
        uniformList = mutableListOf(
            "Camiseta",
            "Gorra",
            "Camiseta y gorra"
        ),
        foodList = mutableListOf(
            "Bebida",
            "Refrigerio",
            "Almuerzo",
            "Pasaboca",
            "Agua",
            "Café",
            "Café y agua"
        )
    )

    fun Context.saveImage(bitmap: Bitmap): Uri? {
        var uri: Uri? = null
        try {
            val fileName = System.nanoTime().toString() + ".png"
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                } else {
                    val directory =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    val file = File(directory, fileName)
                    put(MediaStore.MediaColumns.DATA, file.absolutePath)
                }
            }

            uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                contentResolver.openOutputStream(it).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.apply {
                        clear()
                        put(MediaStore.Audio.Media.IS_PENDING, 0)
                    }
                    contentResolver.update(uri, values, null, null)
                }
            }
            return uri
        } catch (e: java.lang.Exception) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                contentResolver.delete(uri, null, null)
            }
            throw e
        }
    }


    fun sessionNumberToArea(programType: String, n: Int): String {
        when (programType) {
            WOMAN -> return when (n) {
                in 1..6 -> "TEJIDO CROCHET"
                in 7..12 -> "TEJIDO MACRAMÉ"
                in 13..18 -> "BISUTERÍA"
                in 19..26 -> "BORDADO"
                else -> "Sesión inválida"
            }

            YOUTH -> {
                return when (n) {
                    in 1..6 -> "TEORIA MUSICAL"
                    in 7..12 -> "PERCUSIÓN"
                    in 13..18 -> "GUITARRA"
                    in 19..26 -> "ACORDEÓN"
                    else -> "Sesión inválida"
                }
            }

            else -> {
                return "Tipo de programa inválido"
            }
        }
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    const val PROFESSIONAL = "PROFESSIONAL"
    const val SUPERVISOR = "SUPERVISOR"
    const val HELPER_PDF = "helper_pdf"


    val DEPARTAMENTOS = listOf(
        "AMAZONAS",
        "ANTIOQUIA",
        "ARAUCA",
        "ATLANTICO",
        "BOLIVAR",
        "BOYACA",
        "CALDAS",
        "CAQUETA",
        "CASANARE",
        "CAUCA",
        "CESAR",
        "CHOCO",
        "CORDOBA",
        "CUNDINAMARCA",
        "GUAINIA",
        "GUAVIARE",
        "HUILA",
        "LA GUAJIRA",
        "MAGDALENA",
        "META",
        "NARINO",
        "NORTE-DE-SANTANDER",
        "PUTUMAYO",
        "QUINDIO",
        "RISARALDA",
        "SAN ANDRES Y PROVIDENCIA",
        "SANTANDER",
        "SUCRE",
        "TOLIMA",
        "VALLE DEL CAUCA",
        "VAUPES",
        "VICHADA"
    )

    val filesPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )


}