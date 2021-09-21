package com.delegation.horizon.model

//Document to contain details of motor insurance
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class MotorPredicate {
    @Id
    var regNumber = ""
    var regYear = 0
    var vehicleMake = ""
    var vehicleModel = ""
    var kmsDriven = ""
    var onRoadAge = ""





}