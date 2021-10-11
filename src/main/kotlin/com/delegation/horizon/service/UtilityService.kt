package com.delegation.horizon.service

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

    fun generateJSONFile(jsonRequest: JSONObject): PartnerMapping {
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingId = generateId()
        partnerMapping.mappingContent = encryptPayload(jsonRequest.toJSONString())
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

    fun fetchMappingById(mappingId: String): String {
        val fetchedMapping: PartnerMapping = partnerMappingRepository.findPartnerMappingByMappingId(mappingId)
        return decryptPayload(fetchedMapping.mappingContent)
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


    open fun encryptPayload(payload: String?): String {
        val aesEncryptor = AES256TextEncryptor()
        aesEncryptor.setPassword(aesSecret)
        return if(payload!!.isEmpty()){
            "Empty Payload"
        } else {
            aesEncryptor.encrypt(payload)
        }
    }

    open fun decryptPayload(payload: String?): String {
        val aesEncryptor = AES256TextEncryptor()
        aesEncryptor.setPassword(aesSecret)
        return if(payload!!.isEmpty()){
            "Empty Payload"
        } else {
            aesEncryptor.decrypt(payload)
        }
    }


}