package com.collaborative.core.repository

import com.collaborative.core.model.Company
import org.springframework.data.repository.CrudRepository

interface CompanyRepository : CrudRepository<Company, Int>