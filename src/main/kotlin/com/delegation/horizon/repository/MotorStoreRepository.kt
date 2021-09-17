package com.delegation.horizon.repository

import com.delegation.horizon.model.MotorStore
import org.springframework.data.mongodb.repository.MongoRepository

interface MotorStoreRepository: MongoRepository<MotorStore, String> {

    fun findByMotorStoreId(motorStoreId: String): MotorStore
    fun deleteByMotorStoreId(motorStoreId: String): String

}