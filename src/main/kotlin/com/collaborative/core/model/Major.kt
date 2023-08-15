package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "majors")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Major(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String?,
    // Define the Many-to-One relationship
    @JsonBackReference // Use this annotation to exclude the 'campus' property from JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", referencedColumnName = "id")
    val campus: Campus?
){
    @JsonProperty("campus_id")
    fun getCampusId(): Int? {
        return campus?.id
    }
}

data class MajorRequest(
    val name: String?,
    val campus_id: Int?
)