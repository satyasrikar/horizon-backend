package com.delegation.horizon.controller
//Partner Controller

import com.delegation.horizon.model.Partner
import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.repository.PartnerRepository
import com.delegation.horizon.service.UtilityService
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*


@RestController
@RequestMapping("/v1/store")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PartnerController(val partnerRepository: PartnerRepository, val utilityService: UtilityService, val partnerMappingRepository: PartnerMappingRepository) {




        @Autowired lateinit var restTemplate: RestTemplate

  @PostMapping("/mapping")
  fun mockMapping(): String? {
      val headers = HttpHeaders()
      headers.accept = Arrays.asList(MediaType.APPLICATION_JSON)
      val entity: HttpEntity<String> = HttpEntity<String>( headers)
      return restTemplate.postForObject("http://localhost:9995/mapping", entity, String().javaClass)


  }




    @PostMapping("/generate")
    fun generateJSONTemplate(@RequestBody jsonRequest: JSONObject): PartnerMapping {
       return utilityService.generateJSONFile(jsonRequest)
    }

    @GetMapping("/mapping/{id}")
    fun findMappingById(@PathVariable mappingId: String): Optional<PartnerMapping> {
        return partnerMappingRepository.findById(mappingId)

    }


}