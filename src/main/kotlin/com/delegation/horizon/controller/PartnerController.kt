package com.delegation.horizon.controller
//Partner Controller

import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.service.UtilityService
import org.json.simple.JSONObject
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PartnerController(
    val utilityService: UtilityService,
    val partnerMappingRepository: PartnerMappingRepository
) {

    //Mocking Service / Mountebank
    @PostMapping("/mapping")
    fun mockMapping(): String? {
        return utilityService.mockMapping()
    }

    //Generate JSON Template / Persist to Mongo
    @PostMapping("/generate")
    fun generateJSONTemplate(@RequestBody jsonRequest: JSONObject): PartnerMapping {
        return utilityService.generateJSONFile(jsonRequest)
    }

    //Fetch Mapping by mappingId
    @GetMapping("/mapping/{id}")
    fun findMappingById(@PathVariable mappingId: String): Optional<PartnerMapping> {
        return partnerMappingRepository.findById(mappingId)
    }


}