package com.collaborative.core.repository

import com.collaborative.core.model.Talent
import org.springframework.data.repository.CrudRepository

interface  TalentRepository : CrudRepository<Talent, Int>