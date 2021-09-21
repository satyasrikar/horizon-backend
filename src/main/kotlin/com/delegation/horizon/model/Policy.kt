package com.delegation.horizon.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Policy {

    @Id
    var policyId = ""

    var policyAmount = 0 //25000

    var policyDescription = "" //Lorem ipsum
}