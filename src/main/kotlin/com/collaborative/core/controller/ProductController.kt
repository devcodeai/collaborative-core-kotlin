package com.collaborative.core.controller

import com.collaborative.core.model.Product
import com.collaborative.core.model.ApiResponse
import com.collaborative.core.model.ProductRequest
import com.collaborative.core.repository.ProductRepository
import com.collaborative.core.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/products")
class ProductController (@Autowired private val productRepository: ProductRepository, private val companyRepository: CompanyRepository) {

    @GetMapping("")
    fun getProductsByCompanyId(@RequestParam company_id: Int): ResponseEntity<ApiResponse<List<Product>>> {

        val company = companyRepository.findById(company_id).orElse(null)
        if (company == null){
            val errorResponse = ApiResponse<List<Product>>(
                status = "Not Found",
                message= "Products with Company ID $company_id Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        val products = productRepository.findByCompany_Id(company_id)
        val response = ApiResponse(
            status = "Success",
            message= "Fetch Products with Company ID $company_id Success",
            data = products
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<ApiResponse<Product>> {
        if (productRequest.name.isNullOrEmpty() || productRequest.company_id == null) {
            val errorResponse = ApiResponse<Product>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val company = companyRepository.findById(productRequest.company_id).orElse(null)
        if (company == null){
            val errorResponse = ApiResponse<Product>(
                status = "Not Found",
                message= "Create Product Failed, Company with ID ${productRequest.company_id} Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val product = Product(id= 0 , name = productRequest.name!!, company = company)
        val createdProduct = productRepository.save(product)
        val response = ApiResponse(
            status = "Success",
            message= "Create Product Success",
            data = createdProduct
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") productId: Int): ResponseEntity<ApiResponse<Product>> {
        val product = productRepository.findById(productId).orElse(null)
        if (product != null) {
            val response = ApiResponse(
                status = "Success",
                message= "Fetch Product with ID $productId Success",
                data = product
            )
            return ResponseEntity.ok(response)
        }

        val errorResponse = ApiResponse<Product>(
            status = "Not Found",
            message= "Product with ID $productId Not Found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @PutMapping("/{id}")
    fun updateProductById(@PathVariable("id") productId: Int, @RequestBody productRequest: ProductRequest): ResponseEntity<ApiResponse<Product>> {
        if (productRequest.name.isNullOrEmpty() || productRequest.company_id == null) {
            val errorResponse = ApiResponse<Product>(
                status = "Failed",
                message = "Bad Request"
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        val existingProduct = productRepository.findById(productId).orElse(null)

        if (existingProduct == null){
            val errorResponse = ApiResponse<Product>(
                status = "Not Found",
                message= "Product with ID $productId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val company = companyRepository.findById(productRequest.company_id).orElse(null)
        if (company == null){
            val errorResponse = ApiResponse<Product>(
                status = "Not Found",
                message= "Update Product Failed, Company with ID ${productRequest.company_id} Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val updatedProduct = existingProduct.copy(name = productRequest.name!!, company = company)
        productRepository.save(updatedProduct)

        val response = ApiResponse(
            status = "Success",
            message= "Update Product with ID $productId Success",
            data = updatedProduct
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") productId: Int): ResponseEntity<ApiResponse<Product>> {
        if (!productRepository.existsById(productId)) {
            val errorResponse = ApiResponse<Product>(
                status = "Not Found",
                message= "Product with ID $productId Not Found"
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }
        productRepository.deleteById(productId)
        val response = ApiResponse<Product>(
            status = "Success",
            message= "Delete Product with ID $productId Success"
        )
        return ResponseEntity.ok(response)
    }
}