package com.collaborative.core.repository

import com.collaborative.core.model.Campus
import org.springframework.data.repository.CrudRepository

interface  CampusRepository : CrudRepository<Campus, Int>