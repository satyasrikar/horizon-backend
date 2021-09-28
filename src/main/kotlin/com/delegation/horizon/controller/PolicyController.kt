package com.delegation.horizon.controller

import com.delegation.horizon.model.Policy
import com.delegation.horizon.repository.PolicyRepository
import com.delegation.horizon.request.MotorInsuranceDTO
import com.delegation.horizon.service.MotorInsuranceService
import com.delegation.horizon.service.UtilityService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PolicyController(val policyRepository: PolicyRepository, val motorInsuranceService: MotorInsuranceService, val utilityService: UtilityService) {

    @GetMapping("/policies")
    fun viewAllPolicies(): List<Policy>{
        return policyRepository.findAll()
    }


    @GetMapping("/policies/{policyId}")
    fun viewPolicyById(@PathVariable policyId: String): Policy {
        return policyRepository.findByPolicyId(policyId)
    }



    @PostMapping("/testpolicies")
    fun addPolicy(@RequestBody policyRequest: Policy): Policy {
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

        val policyIdGenerator = random.nextInt(1000)
        val policyAutoGenId =
            generatedString.substring(0, 2).toUpperCase() + policyIdGenerator + generatedString.substring(5, 9)
                .toUpperCase()
        policyRequest.policyId = policyAutoGenId
        return policyRepository.save(policyRequest)
    }

    @PostMapping("/testTemplate")
    fun testTemplate(): String {
        return utilityService.generatePropertiesFile()
    }



    @PutMapping("/policies")
    fun updatePolicy(@RequestBody policyRequest: Policy): Policy {
        return policyRepository.save(policyRequest)
    }

    @DeleteMapping("/policies/{policyId}")
    fun deletePolicyFromStore(@PathVariable policyId: String) : String {
        policyRepository.deleteByPolicyId(policyId)
        return "Deleted"
    }

    @PostMapping("/policies")
    fun addNewMotorInsurance(@RequestBody motorInsuranceRequest: MotorInsuranceDTO): Policy {
        return motorInsuranceService.generatePolicy(motorInsuranceRequest)
    }

}