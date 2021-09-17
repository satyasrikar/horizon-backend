package com.delegation.horizon.repository

import com.delegation.horizon.model.MotorPredicate
import org.springframework.data.mongodb.repository.MongoRepository

interface MotorPredicateRepository: MongoRepository<MotorPredicate, Int> {

}