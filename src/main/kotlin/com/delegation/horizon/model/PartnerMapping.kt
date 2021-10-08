package com.delegation.horizon.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class PartnerMapping() {
    @Id
    var mappingId = ""


    var mappingContent = ""

}