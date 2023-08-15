package com.collaborative.core.controller

import com.collaborative.core.model.Major
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.model.MajorRequest
import com.collaborative.core.repository.MajorRepository
import com.collaborative.core.repository.CampusRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/majors")
class MajorController (@Autowired private val majorRepository: MajorRepository, private val campusRepository: CampusRepository) {

    @GetMapping("")
    fun getMajorsByCampusId(@RequestParam campus_id: Int): ResponseEntity<ApiResponse<List<Major>>> {

        val campus = campusRepository.findById(campus_id).orElse(null)
        if (campus == null){
            val errorResponse = ApiResponse<List<Major>>(
                status = "Not Found",
                message= "Majors with Campus ID $campus_id Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        val majors = majorRepository.findByCampus_Id(campus_id)
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Majors with Campus ID $campus_id Success",
            data = majors
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createMajor(@RequestBody majorRequest: MajorRequest): ResponseEntity<ApiResponse<Major>> {
        if (majorRequest.name.isNullOrEmpty() || majorRequest.campus_id == null) {
            val errorResponse = ApiResponse<Major>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val campus = campusRepository.findById(majorRequest.campus_id).orElse(null)
        if (campus == null){
            val errorResponse = ApiResponse<Major>(
                status = "Not Found",
                message= "Create Major Failed, Campus with ID ${majorRequest.campus_id} Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val major = Major(id= 0 , name = majorRequest.name, campus = campus)
        val createdMajor = majorRepository.save(major)
        val response = ApiResponse(
            status = "Success",
            message= "Create Major Success",
            data = createdMajor
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getMajorById(@PathVariable("id") majorId: Int): ResponseEntity<ApiResponse<Major>> {
        val major = majorRepository.findById(majorId).orElse(null)
        if (major != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Major with ID $majorId Success",
                data = major
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Major>(
            status = "Not Found",
            message= "Major with ID $majorId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateMajorById(@PathVariable("id") majorId: Int, @RequestBody majorRequest: MajorRequest): ResponseEntity<ApiResponse<Major>> {
        if (majorRequest.name.isNullOrEmpty() || majorRequest.campus_id == null) {
            val errorResponse = ApiResponse<Major>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val existingMajor = majorRepository.findById(majorId).orElse(null)

        if (existingMajor == null){
            val errorResponse = ApiResponse<Major>(
                status = "Not Found",
                message= "Major with ID $majorId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val campus = campusRepository.findById(majorRequest.campus_id).orElse(null)
        if (campus == null){
            val errorResponse = ApiResponse<Major>(
                status = "Not Found",
                message= "Update Major Failed, Campus with ID ${majorRequest.campus_id} Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val updatedMajor = existingMajor.copy(name = majorRequest.name, campus = campus)
        majorRepository.save(updatedMajor)

        val response = ApiResponse(
            status = "Success",
            message= "Update Major with ID $majorId Success",
            data = updatedMajor
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") majorId: Int): ResponseEntity<ApiResponse<Major>> {
        if (!majorRepository.existsById(majorId)) {
            val errorResponse = ApiResponse<Major>(
                status = "Not Found",
                message= "Major with ID $majorId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        majorRepository.deleteById(majorId)
        val response = ApiResponse<Major>(
            status = "Success",
            message= "Delete Major with ID $majorId Success",
            data= Major(null, null, null)
        )
        return ResponseEntity.ok(response)
    }
}