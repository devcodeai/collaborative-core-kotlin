package com.collaborative.core.model

data class ApiResponse<T>(
    val status: String?,
    val message: String?,
    val data: T? = null
)
