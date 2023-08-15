package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import jakarta.persistence.*

@Entity
@Table(name = "communities")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Community(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String?,
    val description: String?,
    val members: String?
)