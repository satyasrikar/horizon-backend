package com.delegation.horizon.service

import com.delegation.horizon.model.MotorPredicate
import com.delegation.horizon.model.User
import com.delegation.horizon.repository.*
import com.delegation.horizon.request.MotorInsuranceDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class MotorInsuranceService(
    val userRepository: UserRepository,
    val policyRepository: PolicyRepository,
    val motorStoreRepository: MotorStoreRepository,
    val partnerRepository: PartnerRepository,
    val motorPredicateRepository: MotorPredicateRepository,
) {

    fun generateId(): String {

        // AutoGenerate Unique Id

        val leftLimit = 97 // letter 'a'
        val rightLimit = 122 // letter 'z'
        val targetStringLength = 10
        val random = Random()
        val generatedString = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength.toLong())
            .collect(
                { StringBuilder() },
                { obj: StringBuilder, codePoint: Int -> obj.appendCodePoint(codePoint) }
            ) { obj: StringBuilder, s: StringBuilder? -> obj.append(s) }
            .toString()

        var randomIdGen = random.nextInt(1000)
        val generatedId =
            generatedString.substring(0, 3).toUpperCase() + randomIdGen + generatedString.substring(5, 8)
                .toUpperCase()
        return generatedId
    }

    fun generateMotorPolicy(motorInsuranceRequest: MotorInsuranceDTO): MotorInsuranceDTO {

        // ================ GOALS ===================
        // Receive Kms Driven, onRoadAge for vehicle
        // Assign a policy Tier to each request
        // Forward the request to Mountebank
        // Mountebank receives and reads policy Tier,
        //              sends back policy Quote/Amount
        // ==========================================

        var userAutoGenId = generateId()

        var doesExistByUserId = userRepository.existsByUserId(userAutoGenId)
        var doesExistByEmail = userRepository.existsByEmail(motorInsuranceRequest.email)

        if (!doesExistByUserId && !doesExistByEmail) {
            val user: User = User()
            val motorPredicate: MotorPredicate = MotorPredicate()
            user.userId = userAutoGenId
            user.address = motorInsuranceRequest.address
            user.insuranceType = motorInsuranceRequest.insuranceType
            user.phone = motorInsuranceRequest.phone
            user.email = motorInsuranceRequest.email
            user.name = motorInsuranceRequest.name
            motorPredicate.kmsDriven = motorInsuranceRequest.kmsDriven
            motorPredicate.regNumber = motorInsuranceRequest.regNumber
            motorPredicate.regYear = motorInsuranceRequest.regYear
            val onRoadAge: Int = 2021 - motorInsuranceRequest.regYear.toInt()
            motorPredicate.onRoadAge = onRoadAge.toString()
            motorPredicate.vehicleMake = motorInsuranceRequest.vehicleMake
            motorPredicate.vehicleModel = motorInsuranceRequest.vehicleModel
            motorPredicateRepository.save(motorPredicate)
            userRepository.save(user)
            motorInsuranceRequest.userId = userAutoGenId
            return motorInsuranceRequest
        } else {
            motorInsuranceRequest.name = "NULL"
            return motorInsuranceRequest
        }


    }




}