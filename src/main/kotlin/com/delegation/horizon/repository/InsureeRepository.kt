package com.delegation.horizon.repository

import com.delegation.horizon.model.Insuree
import org.springframework.data.mongodb.repository.MongoRepository

interface InsureeRepository : MongoRepository<Insuree, Int> {

    fun existsByInsureeId(insureeId: String): Boolean
    fun existsByEmail(insureeEmail: String): Boolean
    fun findByEmail(insureeEmail: String): Insuree
    fun findByInsureeId(insuree: String): Insuree
    fun deleteByInsureeId(insureeId: String): String
}