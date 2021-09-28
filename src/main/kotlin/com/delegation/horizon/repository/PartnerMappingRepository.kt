package com.delegation.horizon.repository

import com.delegation.horizon.model.Partner
import com.delegation.horizon.model.PartnerMapping
import org.springframework.data.mongodb.repository.MongoRepository

interface PartnerMappingRepository : MongoRepository<PartnerMapping, String> {

}