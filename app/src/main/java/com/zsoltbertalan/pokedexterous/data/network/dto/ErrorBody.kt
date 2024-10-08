package com.zsoltbertalan.pokedexterous.data.network.dto

import kotlinx.serialization.Serializable

@Suppress("PropertyName", "ConstructorParameterNaming")
@Serializable
data class ErrorBody(val success: Boolean, val status_code: Int, val status_message: String)
