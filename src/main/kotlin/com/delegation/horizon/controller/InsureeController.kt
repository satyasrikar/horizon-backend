package com.delegation.horizon.controller

import com.delegation.horizon.model.Insuree
import com.delegation.horizon.model.MotorPredicate
import com.delegation.horizon.repository.InsureeRepository
import com.delegation.horizon.repository.MotorPredicateRepository
import com.delegation.horizon.request.MotorInsuranceDTO
import com.delegation.horizon.service.MotorInsuranceService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class InsureeController(
    val insureeRepository: InsureeRepository,
    val motorPredicateRepository: MotorPredicateRepository,
    val motorInsuranceService: MotorInsuranceService
) {

    @GetMapping("/insurees")
    fun getAllInsurees(): List<Insuree> {
        return insureeRepository.findAll()
    }

    @GetMapping("/insurees/{insureeId}")
    fun findInsureesById(@PathVariable insureeId: String): Insuree {
        return insureeRepository.findByInsureeId(insureeId)
    }

    @PostMapping("/insurees")
    fun addNewMotorInsurance(@RequestBody motorInsuranceRequest: MotorInsuranceDTO): MotorInsuranceDTO {
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

        val insureeIdGenerator = random.nextInt(1000)
        val insureeAutoGenId =
            generatedString.substring(0, 2).toUpperCase() + insureeIdGenerator + generatedString.substring(5, 9)
                .toUpperCase()
        var doesExistByInsureeId = insureeRepository.existsByInsureeId(insureeAutoGenId)
        var doesExistByEmail = insureeRepository.existsByEmail(motorInsuranceRequest.email)


        if (!doesExistByInsureeId && !doesExistByEmail) {
            val insuree: Insuree = Insuree()
            val motorPredicate: MotorPredicate = MotorPredicate()
            insuree.insureeId = insureeAutoGenId
            insuree.address = motorInsuranceRequest.address
            insuree.insuranceType = motorInsuranceRequest.insuranceType
            insuree.phone = motorInsuranceRequest.phone
            insuree.email = motorInsuranceRequest.email
            insuree.name = motorInsuranceRequest.name
            motorPredicate.kmsDriven = motorInsuranceRequest.kmsDriven
            motorPredicate.regNumber = motorInsuranceRequest.regNumber
            motorPredicate.regYear = motorInsuranceRequest.regYear
            motorPredicate.vehicleMake = motorInsuranceRequest.vehicleMake
            motorPredicate.vehicleModel = motorInsuranceRequest.vehicleModel
            motorPredicateRepository.save(motorPredicate)
            insureeRepository.save(insuree)
            motorInsuranceRequest.insureeId = insureeAutoGenId
            motorInsuranceService.generateMotorPredicatePolicy(motorInsuranceRequest)


            return motorInsuranceRequest
        } else
            motorInsuranceRequest.name = "NULL"
        return motorInsuranceRequest
    }

    @PutMapping("/insurees")
    fun updateInsuree(@RequestBody insuree: Insuree): Insuree {
        var doesExistByEmail = insureeRepository.existsByEmail(insuree.email)
        if (doesExistByEmail) {
            val fetchedInsuree: Insuree = insureeRepository.findByEmail(insuree.email)
            fetchedInsuree.name = insuree.name
            fetchedInsuree.insuranceType = insuree.insuranceType
            fetchedInsuree.phone = insuree.phone
            fetchedInsuree.address = insuree.address

            return insureeRepository.save(fetchedInsuree)

        } else {
            insuree.insureeId = "NULL"
            insuree.name = "NULL"
            insuree.address = "NULL"
            insuree.insuranceType = "NULL"
            insuree.email = "NULL"
            insuree.phone = "NULL"
            return insuree
        }

    }

    @DeleteMapping("/insurees/{insureeId}")
    fun deleteInsuree(@PathVariable insureeId: String): String {
        var doesExistByInsureeId = insureeRepository.existsByInsureeId(insureeId)
        println(doesExistByInsureeId)
        println(insureeId)
        if (doesExistByInsureeId) {
            insureeRepository.deleteByInsureeId(insureeId)
            return "Deleted"
        } else
            return "Does not exist"
    }
}