package com.collaborative.core.controller

import com.collaborative.core.model.Campus
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.repository.CampusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/campuses")
class CampusController(@Autowired private val campusRepository: CampusRepository) {

    @GetMapping("")
    fun getAllCompanies(): ResponseEntity<ApiResponse<List<*>>> {
        val companies = campusRepository.findAll()
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Campuses Success",
            data = companies as List<*>
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createCampus(@RequestBody campus: Campus): ResponseEntity<ApiResponse<Campus>> {
        if (campus.university_name.isNullOrEmpty()|| campus.location.isNullOrEmpty() || campus.website.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Campus>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val createdCampus = campusRepository.save(campus)
        val response = ApiResponse(
            status = "Success",
            message= "Create Campus Success",
            data = createdCampus
        )
        return ResponseEntity.ok(response)
//        return ResponseEntity(createdCampus, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getCampusById(@PathVariable("id") campusId: Int): ResponseEntity<ApiResponse<Campus>> {
        val campus = campusRepository.findById(campusId).orElse(null)
        if (campus != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Campus with ID $campusId Success",
                data = campus
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Campus>(
            status = "Not Found",
            message= "Campus with ID $campusId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateCampusById(@PathVariable("id") campusId: Int, @RequestBody campus: Campus): ResponseEntity<ApiResponse<Campus>> {
        if (campus.university_name.isNullOrEmpty()|| campus.location.isNullOrEmpty() || campus.website.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Campus>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }


        val existingCampus = campusRepository.findById(campusId).orElse(null)

        if (existingCampus == null){
            val errorResponse = ApiResponse<Campus>(
                status = "Not Found",
                message= "Campus with ID $campusId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }


        val updatedCampus = existingCampus.copy(university_name = campus.university_name, location = campus.location, website = campus.website)
        campusRepository.save(updatedCampus)

        val response = ApiResponse(
            status = "Success",
            message= "Update Campus with ID $campusId Success",
            data = updatedCampus
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") campusId: Int): ResponseEntity<ApiResponse<Campus>> {
        if (!campusRepository.existsById(campusId)) {
            val errorResponse = ApiResponse<Campus>(
                status = "Not Found",
                message= "Campus with ID $campusId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        campusRepository.deleteById(campusId)
        val response = ApiResponse<Campus>(
            status = "Success",
            message= "Delete Campus with ID $campusId Success",
            data = Campus(null, null, null, null)
        )
        return ResponseEntity.ok(response)
    }
}