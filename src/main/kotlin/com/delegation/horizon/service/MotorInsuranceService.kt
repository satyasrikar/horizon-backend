package com.delegation.horizon.service

import com.delegation.horizon.model.Policy
import com.delegation.horizon.repository.*
import com.delegation.horizon.request.MotorInsuranceDTO
import org.springframework.stereotype.Service
import java.util.*

@Service
class MotorInsuranceService(

    val policyRepository: PolicyRepository,

    val partnerRepository: PartnerRepository,

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






}