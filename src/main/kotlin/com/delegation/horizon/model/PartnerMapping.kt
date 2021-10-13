package com.delegation.horizon.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class PartnerMapping() {
    @Id
    var mappingId = ""
    var partnerName = ""
    var mappingContent = ""
    var isApproved = false
    override fun toString(): String {
        return "PartnerMapping(mappingId='$mappingId', partnerName='$partnerName', mappingContent='$mappingContent', isApproved=$isApproved)"
    }


}