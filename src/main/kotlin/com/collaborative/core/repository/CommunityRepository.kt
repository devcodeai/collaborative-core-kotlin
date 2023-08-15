package com.collaborative.core.repository

import com.collaborative.core.model.Community
import org.springframework.data.repository.CrudRepository

interface  CommunityRepository : CrudRepository<Community, Int>