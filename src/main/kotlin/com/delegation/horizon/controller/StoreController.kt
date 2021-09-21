package com.delegation.horizon.controller

import com.delegation.horizon.model.MotorStore
import com.delegation.horizon.repository.MotorStoreRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class StoreController(val motorStoreRepository: MotorStoreRepository) {


    @GetMapping("/test")
    fun testYear() {
        var d = Date()
        var r = d.year
        println(r)
    }
    @GetMapping("/motors")
    fun viewAllMotorsInStore(): List<MotorStore> {
        return motorStoreRepository.findAll()
    }

    @GetMapping("/motors/{motorStoreId}")
    fun viewMotorStoreById(@PathVariable motorStoreId: String): MotorStore {
        return motorStoreRepository.findByMotorStoreId(motorStoreId)
    }

    @PostMapping("/motors")
    fun addToMotorStore(@RequestBody motorStoreRequest: MotorStore): MotorStore {
        return motorStoreRepository.save(motorStoreRequest)
    }

    @PutMapping("/motors")
    fun updateMotorStore(@RequestBody motorStoreRequest: MotorStore): MotorStore {
        return motorStoreRepository.save(motorStoreRequest)
    }

    @DeleteMapping("/motors/{motorStoreId}")
    fun deleteMotorFromStore(@PathVariable motorStoreId: String) : String {
        motorStoreRepository.deleteByMotorStoreId(motorStoreId)
        return "Deleted"
    }
}