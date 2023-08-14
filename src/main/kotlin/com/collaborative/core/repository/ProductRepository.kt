package com.collaborative.core.repository

import com.collaborative.core.model.Product
import org.springframework.data.repository.CrudRepository

//interface ProductRepository : CrudRepository<Product, Int>
interface ProductRepository : CrudRepository<Product, Int> {
    fun findByCompany_Id(company_id: Int): List<Product>
}