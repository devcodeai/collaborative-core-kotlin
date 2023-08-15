package com.collaborative.core.controller

import com.collaborative.core.model.Talent
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.repository.TalentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/talents")
class TalentController(@Autowired private val talentRepository: TalentRepository) {

    @GetMapping("")
    fun getAllTalents(): ResponseEntity<ApiResponse<List<*>>> {
        val companies = talentRepository.findAll()
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Talents Success",
            data = companies as List<*>
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createTalent(@RequestBody talent: Talent): ResponseEntity<ApiResponse<Talent>> {
        if (talent.name.isNullOrEmpty()|| talent.email.isNullOrEmpty() || talent.skills.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Talent>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val createdTalent = talentRepository.save(talent)
        val response = ApiResponse(
            status = "Success",
            message= "Create Talent Success",
            data = createdTalent
        )
        return ResponseEntity.ok(response)
//        return ResponseEntity(createdTalent, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTalentById(@PathVariable("id") talentId: Int): ResponseEntity<ApiResponse<Talent>> {
        val talent = talentRepository.findById(talentId).orElse(null)
        if (talent != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Talent with ID $talentId Success",
                data = talent
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Talent>(
            status = "Not Found",
            message= "Talent with ID $talentId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateTalentById(@PathVariable("id") talentId: Int, @RequestBody talent: Talent): ResponseEntity<ApiResponse<Talent>> {
        if (talent.name.isNullOrEmpty()|| talent.email.isNullOrEmpty() || talent.skills.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Talent>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }


        val existingTalent = talentRepository.findById(talentId).orElse(null)

        if (existingTalent == null){
            val errorResponse = ApiResponse<Talent>(
                status = "Not Found",
                message= "Talent with ID $talentId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }


        val updatedTalent = existingTalent.copy(name = talent.name, email = talent.email, skills = talent.skills)
        talentRepository.save(updatedTalent)

        val response = ApiResponse(
            status = "Success",
            message= "Update Talent with ID $talentId Success",
            data = updatedTalent
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") talentId: Int): ResponseEntity<ApiResponse<Talent>> {
        if (!talentRepository.existsById(talentId)) {
            val errorResponse = ApiResponse<Talent>(
                status = "Not Found",
                message= "Talent with ID $talentId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        talentRepository.deleteById(talentId)
        val response = ApiResponse<Talent>(
            status = "Success",
            message= "Delete Talent with ID $talentId Success",
            data = Talent(null, null, null, null)
        )
        return ResponseEntity.ok(response)
    }
}