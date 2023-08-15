package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*

@Entity
@Table(name = "talents")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Talent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String?,
    val email: String?,
    val skills: String?
)