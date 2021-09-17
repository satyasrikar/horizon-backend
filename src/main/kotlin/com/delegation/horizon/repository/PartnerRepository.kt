package com.delegation.horizon.repository

import com.delegation.horizon.model.Partner
import org.springframework.data.mongodb.repository.MongoRepository

interface PartnerRepository : MongoRepository<Partner, Int> {
    fun deleteByPartnerId(partnerId: String) : Partner
    fun findByPartnerId(partnerId: String) : Partner
}