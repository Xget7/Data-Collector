package com.sublimetech.supervisor.data.local.converters

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.sublimetech.supervisor.data.model.family.FamilyAssistanceDto
import com.sublimetech.supervisor.data.model.family.FamilyDto
import com.sublimetech.supervisor.data.model.family.FamilyMemberDto
import com.sublimetech.supervisor.data.model.family.FamilyRelationship
import com.sublimetech.supervisor.data.model.family.ProgramFeedback
import com.sublimetech.supervisor.data.model.youtAndWoman.FormDto
import com.sublimetech.supervisor.domain.model.youthAndWoman.Form
import java.lang.reflect.Type

class Converters {

    val gson = GsonBuilder()
        .registerTypeAdapter(MutableState::class.java, MutableStateTypeAdapter())
        .registerTypeAdapter(
            object : TypeToken<MutableState<String>>() {}.type,
            MutableStateInstanceCreator()
        )
        .create()

    @TypeConverter
    fun toStringList(json: String): List<String> {
        return try {
            gson.fromJson<List<String>>(json) //using extension function
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }


    @TypeConverter
    fun toFamilyAssistance(json: String): FamilyAssistanceDto {
        return try {
            gson.fromJson<FamilyAssistanceDto>(json) //using extension function
        } catch (e: Exception) {
            FamilyAssistanceDto()
        }
    }

    @TypeConverter
    fun fromFamilyAssistance(value: FamilyAssistanceDto): String {
        return gson.toJson(value)
    }


    @TypeConverter
    fun toFamilyRelationship(json: String): FamilyRelationship {
        return try {
            gson.fromJson<FamilyRelationship>(json) //using extension function
        } catch (e: Exception) {
            FamilyRelationship()
        }
    }

    @TypeConverter
    fun fromFamilyRelationship(value: FamilyRelationship): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMembers(json: String): List<FamilyMemberDto> {
        return try {
            gson.fromJson(json) //using extension function
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromMembers(value: List<FamilyMemberDto>): String {
        return gson.toJson(value)
    }


    @TypeConverter
    fun toProgramFeedback(json: String): ProgramFeedback {
        return try {
            gson.fromJson(json) //using extension function
        } catch (e: Exception) {
            ProgramFeedback()
        }
    }

    @TypeConverter
    fun fromProgramFeedback(value: ProgramFeedback): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromMutableString(mutableString: MutableState<String>): String {
        return gson.toJson(mutableString.value)
    }

    @TypeConverter
    fun toMutableString(json: String): MutableState<String> {
        val type = object : TypeToken<String>() {}.type
        val value = gson.fromJson<String>(json, type)
        return mutableStateOf(value)
    }

    @TypeConverter
    fun toFamilyDto(json: String): FamilyDto {
        return gson.fromJson(json, FamilyDto::class.java)
    }

    @TypeConverter
    fun fromFamilyDto(value: FamilyDto): String {
        return gson.toJson(value)
    }


    @TypeConverter
    fun toFormDto(json: String): FormDto {
        return try {
            gson.fromJson(json) //using extension function
        } catch (e: Exception) {
            FormDto()
        }
    }

    @TypeConverter
    fun fromFormDto(value: FormDto): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toForm(json: String): Form {
        return try {
            gson.fromJson(json) //using extension function
        } catch (e: Exception) {
            Form()
        }
    }

    @TypeConverter
    fun fromForm(value: Form): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromHashMap(value: HashMap<String, Uri>?): String? {
        if (value == null) {
            return null
        }
        val gson = gson
        val type = object : TypeToken<HashMap<String, Uri>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toHashMap(value: String?): HashMap<String, Uri>? {
        if (value == null) {
            return null
        }
        val gson = gson
        val type = object : TypeToken<HashMap<String, Uri>>() {}.type
        return gson.fromJson(value, type)
    }


}

class MutableStateInstanceCreator : InstanceCreator<MutableState<String>> {
    override fun createInstance(type: Type?): MutableState<String> {
        return mutableStateOf("")
    }
}

class MutableStateTypeAdapter : TypeAdapter<MutableState<String>>() {

    override fun write(out: com.google.gson.stream.JsonWriter?, value: MutableState<String>?) {
        out?.value(value?.value)
    }

    override fun read(`in`: com.google.gson.stream.JsonReader?): MutableState<String> {
        return mutableStateOf(`in`?.nextString() ?: "")
    }
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson<T>(json, object : TypeToken<T>() {}.type)