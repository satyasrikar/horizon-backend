package com.delegation.horizon.request
//DTO to contain details of motor insurance request

class MotorInsuranceDTO {
    var userId = ""
    var name = ""
    var address = ""
    var email = ""
    var phone = ""
    var insuranceType = ""
    var regNumber = ""
    var regYear = 0
    var vehicleMake = ""
    var vehicleModel = ""
    var kmsDriven = ""
    var engineCc = 0;
    var vehiclePrice = 0.0;
    var partnerDiscount = 0.0;
    val partnerPremium: Double = 0.0;



    override fun toString(): String {
        return "MotorInsuranceDTO(userId='$userId', name='$name', address='$address', email='$email', phone='$phone', insuranceType='$insuranceType', regNumber='$regNumber', regYear=$regYear, vehicleMake='$vehicleMake', vehicleModel='$vehicleModel', kmsDriven='$kmsDriven', engineCc=$engineCc, vehiclePrice=$vehiclePrice, partnerDiscount=$partnerDiscount, partnerPremium=$partnerPremium)"
    }




}