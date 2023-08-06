package com.sublimetech.supervisor.domain.model.WomanForm

data class Deliverables(
    val id: String = "",
    var image: String = "",//mutableStateOf(""),
    var type: String = "",//mutableStateOf(""),
    var derivable: String = "",//mutableStateOf(""),
    var quantity: Int = 0//mutableStateOf(0)
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            "image" to image,
            "type" to type,
            "derivable" to derivable,
            "quantity" to quantity
        )
    }
}