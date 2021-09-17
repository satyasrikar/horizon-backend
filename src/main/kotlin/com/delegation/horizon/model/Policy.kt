package com.delegation.horizon.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Policy {

    @Id
    var policyId = ""

    var policyAmount = "" //25000

    var policyTier = "" //A or B or C

    var policyDescription = "" //Lorem ipsum
}