package com.delegation.horizon.controller
//Partner Controller

import com.delegation.horizon.model.MappingRequestDTO
import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.service.UtilityService
import org.json.simple.JSONObject
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PartnerController(
    val utilityService: UtilityService
) {

    //Mocking Service / Mountebank
    @PostMapping("/mapping")
    fun mockMapping(): String? {
        return utilityService.mockMapping()
    }

    //Generate JSON Template / Persist to Mongo
    @PostMapping("/generate")
    fun generateMapping(@RequestBody jsonRequest: JSONObject, @RequestParam partnerName: String): PartnerMapping {
        println(partnerName)
        println(jsonRequest)
        return utilityService.generateMapping(jsonRequest, partnerName)
    }

    @PostMapping("/encrypt")
    fun encryptPayload(@RequestBody requestBody: String) : String {
        return utilityService.encryptPayload(requestBody)
    }

    @PostMapping("/decrypt")
    fun decryptPayload(@RequestBody requestBody: String) : String {
        return utilityService.decryptPayload(requestBody)
    }

    @GetMapping("/mapping/{mappingId}")
    fun viewMappingById(@PathVariable mappingId:String) : String {
        return utilityService.viewMappingById(mappingId)
    }

    @GetMapping("/mapping/all")
    fun viewAllMappings() : List<PartnerMapping> {
        return utilityService.viewAllMappings()
    }

    @PutMapping("/mapping/{mappingId}")
    fun approveMapping(@PathVariable mappingId: String): String {
        return utilityService.approvePartnerMapping(mappingId)
    }


}