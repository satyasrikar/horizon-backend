package com.delegation.horizon.controller

import com.delegation.horizon.model.MotorStore
import com.delegation.horizon.repository.MotorStoreRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class MotorStoreController(val motorStoreRepository: MotorStoreRepository) {

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