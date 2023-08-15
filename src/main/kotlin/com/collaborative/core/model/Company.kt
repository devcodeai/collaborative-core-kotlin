package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*

@Entity
@Table(name = "companies")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String?,
    val address: String?,
    val email: String?,
    val phone: String?
)