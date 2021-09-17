package com.delegation.horizon.repository

import com.delegation.horizon.model.Policy
import org.springframework.data.mongodb.repository.MongoRepository

interface PolicyRepository: MongoRepository<Policy, Int> {
    fun findByPolicyId(policyId:String) : Policy
    fun deleteByPolicyId(policyId: String): Policy
}