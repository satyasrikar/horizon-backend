package com.delegation.horizon.repository

import com.delegation.horizon.model.PartnerMapping
import org.springframework.data.mongodb.repository.MongoRepository

interface PartnerMappingRepository : MongoRepository<PartnerMapping, String> {

    fun findPartnerMappingByMappingId(mappingId:String): PartnerMapping

}