package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*

@Entity
@Table(name = "campuses")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Campus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val university_name: String?,
    val location: String?,
    val website: String?
)