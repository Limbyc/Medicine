package com.valance.medicine

import kotlinx.serialization.Serializable

@Serializable
data class Notes(
    val id: Int,
    val body: String,
)
