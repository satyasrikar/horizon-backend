package com.delegation.horizon

import com.delegation.horizon.model.MappingRequestDTO
import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.service.UtilityService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
class HorizonDelegationServerApplicationTests {

    @Autowired
    lateinit var utilityService: UtilityService

    @MockBean
    lateinit var partnerMappingRepository: PartnerMappingRepository


    @Test
    fun generateIdLengthTest() {
        Assertions.assertEquals(9, utilityService.generateId().length)
    }

    @Test
    fun `Test AESEncryptor with request String`() {
        val requestString = "horizon"
        Assertions.assertEquals(64, utilityService.encryptPayload(requestString).length)
        Assertions.assertEquals(
            requestString.length,
            utilityService.decryptPayload(utilityService.encryptPayload(requestString)).length
        )
    }

    @Test
    fun `Test AESEncryptor with empty request string`() {
        val requestStringEmpty = ""
        val response = "Empty Payload"
        Assertions.assertEquals(response, utilityService.encryptPayload(requestStringEmpty))
        Assertions.assertEquals(response, utilityService.decryptPayload(requestStringEmpty))
    }

//    @Test
//    fun `Generate Mapping Test`() {
//        val testJsonString = "{   \"partnerDiscount\":0.0,\n" +
//                "    \"partnerPremium\":0.312}"
//
//        val testMappingId = "MAPNG0001"
//        val mappingRequestDTO = MappingRequestDTO()
//        mappingRequestDTO.partnerName = "testPartner"
//        mappingRequestDTO.mappingContent = testJsonString
//
//        val partnerMapping = PartnerMapping()
//        partnerMapping.mappingContent = utilityService.encryptPayload(mappingRequestDTO.mappingContent)
//        partnerMapping.isApproved = false
//        partnerMapping.mappingId = testMappingId
//        partnerMapping.partnerName = mappingRequestDTO.partnerName
//        Mockito.`when`(partnerMappingRepository.save(partnerMapping)).thenReturn(partnerMapping)
//        val partnerMappingResponse = utilityService.generateMapping(mappingRequestDTO)
//        Assertions.assertEquals(9, partnerMappingResponse.mappingId.length)
//        Assertions.assertEquals(128, partnerMappingResponse.mappingContent.length)
//    }

    @Test
    fun `View Partner Mapping By Id Test`() {
        val mappingId = "MAPNG0001"
        val testJsonString = "{   \"partnerDiscount\":0.0,\n" +
                "    \"partnerPremium\":0.312}"
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingContent = utilityService.encryptPayload(testJsonString)
        partnerMapping.isApproved = false
        partnerMapping.mappingId = mappingId

        Mockito.`when`(partnerMappingRepository.findPartnerMappingByMappingId(mappingId)).thenReturn(partnerMapping)
        Assertions.assertEquals(testJsonString, utilityService.viewMappingById(mappingId))
    }

    @Test
    fun `View All Partner Mappings Test`() {
        val mappingId = "MAPNG0001"
        val testJsonString = "{   \"partnerDiscount\":0.0,\n" +
                "    \"partnerPremium\":0.312}"
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingContent = utilityService.encryptPayload(testJsonString)
        partnerMapping.isApproved = false
        partnerMapping.mappingId = mappingId

        Mockito.`when`(partnerMappingRepository.findAll()).thenReturn(listOf(partnerMapping))
        Assertions.assertEquals(1, utilityService.viewAllMappings().size)
    }

    @Test
    fun `Approve partner mapping Test`() {
        val mappingId = "MAPNG0001"
        val testJsonString = "{   \"partnerDiscount\":0.0,\n" +
                "    \"partnerPremium\":0.312}"
        val partnerMapping = PartnerMapping()
        partnerMapping.mappingContent = utilityService.encryptPayload(testJsonString)
        partnerMapping.isApproved = true
        partnerMapping.mappingId = mappingId

        Mockito.`when`(partnerMappingRepository.findPartnerMappingByMappingId(mappingId)).thenReturn(partnerMapping)
        Assertions.assertEquals("Mapping Approved", utilityService.approvePartnerMapping(mappingId))

    }


}
