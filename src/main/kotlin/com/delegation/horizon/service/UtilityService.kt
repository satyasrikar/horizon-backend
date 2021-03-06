package com.delegation.horizon.service

import com.delegation.horizon.model.MappingRequestDTO
import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import org.jasypt.util.text.AES256TextEncryptor
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.util.*


@Service
class UtilityService() {

    @Autowired
    lateinit var partnerMappingRepository: PartnerMappingRepository

    @Autowired
    lateinit var restTemplate: RestTemplate

    var aesSecret: String = System.getenv("aes.passcode") ?: "horizonCore"
    var mockServiceUrl: String = System.getenv("mountebank.url") ?: "http://localhost:9995/mapping/mock"

    fun generateMapping(jsonRequest: JSONObject, partnerName: String): PartnerMapping {
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingId = generateId()
        partnerMapping.mappingContent = encryptPayload(jsonRequest.toJSONString())
        partnerMapping.isApproved = false
        partnerMapping.partnerName = partnerName
        try {
            partnerMappingRepository.save(partnerMapping)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return partnerMapping
    }



    fun mockMapping(): String? {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val entity: HttpEntity<String> = HttpEntity<String>(headers)
        return restTemplate.postForObject(mockServiceUrl, entity, String().javaClass)
    }


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
        val randomIdGen = random.nextInt(1000)
        return generatedString.substring(0, 3).uppercase(Locale.getDefault()) + randomIdGen + generatedString.substring(
            5,
            8
        )
            .uppercase(Locale.getDefault())
    }


    fun encryptPayload(payload: String?): String {
        val aesEncryptor = AES256TextEncryptor()
        aesEncryptor.setPassword(aesSecret)
        return if(payload!!.isEmpty()){
            "Empty Payload"
        } else {
            aesEncryptor.encrypt(payload)
        }
    }

    fun decryptPayload(payload: String?): String {
        val aesEncryptor = AES256TextEncryptor()
        aesEncryptor.setPassword(aesSecret)
        return if(payload!!.isEmpty()){
            "Empty Payload"
        } else {
            aesEncryptor.decrypt(payload)
        }
    }

    fun viewMappingById(mappingId: String) : String {
        val fetchedMapping = partnerMappingRepository.findPartnerMappingByMappingId(mappingId)
        val jsonResponse = decryptPayload(fetchedMapping.mappingContent)
        return jsonResponse
    }

    fun viewAllMappings() : List<PartnerMapping> {
        return partnerMappingRepository.findAll()
    }

    fun approvePartnerMapping(mappingId: String) : String {
        val fetchedMapping = partnerMappingRepository.findPartnerMappingByMappingId(mappingId)
        fetchedMapping.isApproved = true
        partnerMappingRepository.save(fetchedMapping)
        return "Mapping Approved"
    }


}