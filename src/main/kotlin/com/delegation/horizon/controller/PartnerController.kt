package com.delegation.horizon.controller

import com.delegation.horizon.model.Partner
import com.delegation.horizon.repository.PartnerRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PartnerController(val partnerRepository: PartnerRepository) {

    @GetMapping("/partners")
    fun viewAllPartners(): List<Partner> {
        return partnerRepository.findAll()
    }

    @GetMapping("/partners/{partnerId}")
    fun viewPartnerById(@PathVariable partnerId: String):Partner {
        return partnerRepository.findByPartnerId(partnerId)
    }



    @PostMapping("/partners")
    fun addPartner(@RequestBody partnersRequest: Partner): Partner {
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

        val partnerIdGenerator = random.nextInt(1000)
        val partnerAutoGenId =
            generatedString.substring(0, 2).toUpperCase() + partnerIdGenerator + generatedString.substring(5, 9)
                .toUpperCase()
        partnersRequest.partnerId = partnerAutoGenId
        return partnerRepository.save(partnersRequest)
    }

    @PutMapping("/partners")
    fun updatePartners(@RequestBody partnersRequest: Partner): Partner {
        return partnerRepository.save(partnersRequest)
    }

    @DeleteMapping("/partners/{partnerId}")
    fun deleteMotorFromStore(@PathVariable partnerId: String) : String {
        partnerRepository.deleteByPartnerId(partnerId)
        return "Deleted"
    }


}