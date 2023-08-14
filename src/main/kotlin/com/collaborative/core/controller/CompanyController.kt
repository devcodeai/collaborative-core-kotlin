package com.collaborative.core.controller

import com.collaborative.core.model.Company
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/companies")
class CompanyController(@Autowired private val companyRepository: CompanyRepository) {

    @GetMapping("")
    fun getAllCompanies(): ResponseEntity<ApiResponse<List<*>>> {
        val companies = companyRepository.findAll()
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Companies Success",
            data = companies as List<*>
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createCompany(@RequestBody company: Company): ResponseEntity<ApiResponse<Company>> {

        if (company.name == null || company.address == null || company.email == null || company.phone == null) {
            val errorResponse = ApiResponse<Company>(
                status = "Failed",
                message = "Bad Request",
            )
            return ResponseEntity.badRequest().body(errorResponse)
        }

        val createdCompany = companyRepository.save(company)
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Companies Success",
            data = createdCompany
        )
        return ResponseEntity.ok(response)
//        return ResponseEntity(createdCompany, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getCompanyById(@PathVariable("id") companyId: Int): ResponseEntity<ApiResponse<Company>> {
        val company = companyRepository.findById(companyId).orElse(null)
        if (company != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Companies Success",
                data = company
            )
            return ResponseEntity.ok(response)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateCompanyById(@PathVariable("id") companyId: Int, @RequestBody company: Company): ResponseEntity<Company> {

        val existingCompany = companyRepository.findById(companyId).orElse(null)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val updatedCompany = existingCompany.copy(name = company.name, email = company.email)
        companyRepository.save(updatedCompany)
        return ResponseEntity(updatedCompany, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") companyId: Int): ResponseEntity<Company> {
        if (!companyRepository.existsById(companyId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        companyRepository.deleteById(companyId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}