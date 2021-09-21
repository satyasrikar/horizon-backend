package com.delegation.horizon.service

import com.delegation.horizon.model.MotorPredicate
import com.delegation.horizon.model.Policy
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

    fun testMethod(motorInsuranceRequest: MotorInsuranceDTO): MotorInsuranceDTO {

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

    fun generatePolicy(motorInsuranceDTO: MotorInsuranceDTO): Policy {

        var currentYear = 2021;
        var age = currentYear - motorInsuranceDTO.regYear; // user
        var enginePower = motorInsuranceDTO.engineCc; //user
        var partnerDiscount = 0.45; //partner store
        var currentPrice: Double = motorInsuranceDTO.vehiclePrice; // motor store
        var depreciatedValue = 0.0; //autogen
        var damagePremium = 0.0; // autogen
        var depreciation = 0.0;
        var gstRate = 0.18;
        var ncbDiscount = 0.0;
        var thirdPartyPremium = 0;


        if (age <= 1) {
            depreciation = 0.15;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        } else if (age > 1 && age <= 2) {
            depreciation = 0.20;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        } else if (age > 2 && age <= 3) {
            depreciation = 0.30;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        } else if (age > 3 && age <= 4) {
            var depreciation = 0.40;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        } else if (age > 4 && age <= 5) {
            depreciation = 0.50;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        } else {
            depreciation = 0.55;
            depreciatedValue = currentPrice - (depreciation * currentPrice)
            println(depreciatedValue)

        }

        println("depreciatedValue = " + depreciatedValue)

        if (age <= 5) {
            if (enginePower >= 1000 && enginePower < 2500) {
                damagePremium = 0.0328;
                currentPrice = (damagePremium* depreciatedValue);

            } else if (enginePower >= 2500 && enginePower < 10000) {
                damagePremium = 0.0344;
                currentPrice = (damagePremium* depreciatedValue);

            } else if (enginePower >= 10000) {
                damagePremium = 0.0312;
                currentPrice = (damagePremium* depreciatedValue);

            }
        } else if (age > 5) {
            if (enginePower >= 1000 && enginePower < 2500) {
                damagePremium = 0.0328;
                currentPrice = (damagePremium* depreciatedValue);

            } else if (enginePower >= 2500 && enginePower < 10000) {
                damagePremium = 0.0344;
                currentPrice = (damagePremium* depreciatedValue);

            } else if (enginePower >= 10000) {
                damagePremium = 0.0312;
                currentPrice = (damagePremium* depreciatedValue);

            }
        }

        println("after Damage Premium = " + currentPrice)


        currentPrice = currentPrice - (partnerDiscount * currentPrice);

        println("after partner discount = " + currentPrice)

        if (age <= 1) {
            ncbDiscount = 0.20;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        } else if (age <= 2) {
            ncbDiscount = 0.25;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        } else if (age <= 3) {
            ncbDiscount = 0.30;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        } else if (age <= 4) {
            ncbDiscount = 0.40;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        } else if (age <= 5) {
            ncbDiscount = 0.50;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        } else if (age > 5) {
            ncbDiscount = 0.55;
            currentPrice = currentPrice - (currentPrice * ncbDiscount)

        }

        println("after ncbDiscount = " + currentPrice)


        if (enginePower >= 1000 && enginePower < 2500) {
            thirdPartyPremium = 2072;
            currentPrice = currentPrice + thirdPartyPremium;


        } else if (enginePower >= 2500 && enginePower < 10000) {
            thirdPartyPremium = 3221;
            currentPrice = currentPrice + thirdPartyPremium;


        } else if (enginePower >= 10000) {
            thirdPartyPremium = 7890;
            currentPrice = currentPrice + thirdPartyPremium;

        }

        println("after third party Premium = " + currentPrice)


        currentPrice = currentPrice + 400;


        var policyAmount = currentPrice + (currentPrice * gstRate)
        println("final policy = " + policyAmount)

        var policy = Policy()
        policy.policyId = "POLC986439854"
        policy.policyAmount = policyAmount.toInt()
        policy.policyDescription = "motor"
        return policy


    }


}