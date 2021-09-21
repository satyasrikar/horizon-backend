package com.delegation.horizon.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Partner {

    @Id
    var partnerId = ""
    var partnerName = ""
    var partnerImageUrl = ""
    var partnerDescription = ""
    var partnerDiscount = 0
    var partnerPremium = 0
    var partnerLicense = 0





}