package com.delegation.horizon

import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.service.UtilityService
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
class HorizonDelegationServerApplicationTests{

    @Autowired
    val utilityService: UtilityService? = null

    @MockBean
    val partnerMappingRepository: PartnerMappingRepository? = null


    @Test
    fun generateIdLengthTest() {
        Assertions.assertEquals(9, utilityService!!.generateId().length)
    }

    @Test
    fun `Test AESEncryptor with request String`() {
        val requestString = "horizon"
        Assertions.assertEquals(64, utilityService!!.encryptPayload(requestString).length)
        Assertions.assertEquals(
            requestString.length,
            utilityService!!.decryptPayload(utilityService!!.encryptPayload(requestString)).length
        )
    }

    @Test
    fun `Test AESEncryptor with empty request string`() {
        val requestStringEmpty = ""
        val response = "Empty Payload"
        Assertions.assertEquals(response, utilityService!!.encryptPayload(requestStringEmpty))
        Assertions.assertEquals(response, utilityService!!.decryptPayload(requestStringEmpty))
    }

    @Test
    fun `Generate Mapping Test`() {
        val testJsonString = "{   \"partnerDiscount\":0.0,\n" +
                "    \"partnerPremium\":0.312}"
        val parser = JSONParser()
        val jsonString = parser.parse(testJsonString) as JSONObject
        val testMappingId = "MAPNG0001"
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingId = testMappingId
        partnerMapping.mappingContent = testJsonString
        Mockito.`when`(partnerMappingRepository!!.save(partnerMapping)).thenReturn(partnerMapping)
        val partnerMappingResponse = utilityService!!.generateMapping(jsonString)
        Assertions.assertEquals(9, partnerMappingResponse.mappingId.length)
        Assertions.assertEquals(108, partnerMappingResponse.mappingContent.length)
    }


}
