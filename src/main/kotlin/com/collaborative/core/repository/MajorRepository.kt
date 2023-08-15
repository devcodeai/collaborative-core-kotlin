package com.collaborative.core.repository

import com.collaborative.core.model.Major
import org.springframework.data.repository.CrudRepository

interface MajorRepository : CrudRepository<Major, Int> {
    fun findByCampus_Id(campus_id: Int): List<Major>
}