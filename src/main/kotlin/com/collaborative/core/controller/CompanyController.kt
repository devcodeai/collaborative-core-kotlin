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
        if (company.name.isNullOrEmpty()|| company.address.isNullOrEmpty() || company.email.isNullOrEmpty() || company.phone.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Company>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val createdCompany = companyRepository.save(company)
        val response = ApiResponse(
            status = "Success",
            message= "Create Company Success",
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
                message= "Fetch Company with ID $companyId Success",
                data = company
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Company>(
            status = "Not Found",
            message= "Company with ID $companyId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateCompanyById(@PathVariable("id") companyId: Int, @RequestBody company: Company): ResponseEntity<ApiResponse<Company>> {
        if (company.name.isNullOrEmpty()|| company.address.isNullOrEmpty() || company.email.isNullOrEmpty() || company.phone.isNullOrEmpty()) {
            val errorResponse = ApiResponse<Company>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }


        val existingCompany = companyRepository.findById(companyId).orElse(null)

        if (existingCompany == null){
            val errorResponse = ApiResponse<Company>(
                status = "Not Found",
                message= "Company with ID $companyId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }


        val updatedCompany = existingCompany.copy(name = company.name, email = company.email, phone = company.phone, address =  company.address)
        companyRepository.save(updatedCompany)

        val response = ApiResponse(
            status = "Success",
            message= "Update Company with ID $companyId Success",
            data = updatedCompany
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") companyId: Int): ResponseEntity<ApiResponse<Company>> {
        if (!companyRepository.existsById(companyId)) {
            val errorResponse = ApiResponse<Company>(
                status = "Not Found",
                message= "Company with ID $companyId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        companyRepository.deleteById(companyId)
        val response = ApiResponse<Company>(
            status = "Success",
            message= "Delete Company with ID $companyId Success",
            data = Company(null, null, null, null, null)
        )
        return ResponseEntity.ok(response)
    }
}