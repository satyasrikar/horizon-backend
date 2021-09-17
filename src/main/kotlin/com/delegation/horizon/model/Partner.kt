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



}