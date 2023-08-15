package com.collaborative.core.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String?,
    // Define the Many-to-One relationship
    @JsonBackReference // Use this annotation to exclude the 'company' property from JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    val company: Company?
){
    @JsonProperty("company_id")
    fun getCompanyId(): Int? {
        return company?.id
    }
}

data class ProductRequest(
    val name: String?,
    val company_id: Int?
)