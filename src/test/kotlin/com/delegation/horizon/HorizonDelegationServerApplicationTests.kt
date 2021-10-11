package com.delegation.horizon

import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.service.UtilityService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class HorizonDelegationServerApplicationTests() {

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


}
