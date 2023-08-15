package com.collaborative.core.controller

import com.collaborative.core.model.Community
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.repository.CommunityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/communities")
class CommunityController(@Autowired private val communityRepository: CommunityRepository) {

    @GetMapping("")
    fun getAllCommunities(): ResponseEntity<ApiResponse<List<*>>> {
        val companies = communityRepository.findAll()
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Communities Success",
            data = companies as List<*>
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createCommunity(@RequestBody community: Community): ResponseEntity<ApiResponse<Community>> {
        if (community.name.isNullOrEmpty()|| community.members.isNullOrEmpty() || community.description.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Community>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val createdCommunity = communityRepository.save(community)
        val response = ApiResponse(
            status = "Success",
            message= "Create Community Success",
            data = createdCommunity
        )
        return ResponseEntity.ok(response)
//        return ResponseEntity(createdCommunity, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getCommunityById(@PathVariable("id") communityId: Int): ResponseEntity<ApiResponse<Community>> {
        val community = communityRepository.findById(communityId).orElse(null)
        if (community != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Community with ID $communityId Success",
                data = community
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Community>(
            status = "Not Found",
            message= "Community with ID $communityId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateCommunityById(@PathVariable("id") communityId: Int, @RequestBody community: Community): ResponseEntity<ApiResponse<Community>> {
        if (community.name.isNullOrEmpty()|| community.members.isNullOrEmpty() || community.description.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Community>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }


        val existingCommunity = communityRepository.findById(communityId).orElse(null)

        if (existingCommunity == null){
            val errorResponse = ApiResponse<Community>(
                status = "Not Found",
                message= "Community with ID $communityId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }


        val updatedCommunity = existingCommunity.copy(name = community.name, description = community.description, members = community.members)
        communityRepository.save(updatedCommunity)

        val response = ApiResponse(
            status = "Success",
            message= "Update Community with ID $communityId Success",
            data = updatedCommunity
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") communityId: Int): ResponseEntity<ApiResponse<Community>> {
        if (!communityRepository.existsById(communityId)) {
            val errorResponse = ApiResponse<Community>(
                status = "Not Found",
                message= "Community with ID $communityId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        communityRepository.deleteById(communityId)
        val response = ApiResponse<Community>(
            status = "Success",
            message= "Delete Community with ID $communityId Success",
            data = Community(null, null, null, null)
        )
        return ResponseEntity.ok(response)
    }
}