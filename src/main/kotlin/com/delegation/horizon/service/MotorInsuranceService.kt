package com.delegation.horizon.service

import com.delegation.horizon.request.MotorInsuranceDTO
import org.springframework.stereotype.Service

@Service
class MotorInsuranceService {

    fun generateMotorPredicatePolicy(motorInsuranceDTO: MotorInsuranceDTO): MotorInsuranceDTO{
        println(motorInsuranceDTO.address)

        return motorInsuranceDTO

    }
}