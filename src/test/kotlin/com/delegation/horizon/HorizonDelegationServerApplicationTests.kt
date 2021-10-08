package com.delegation.horizon

import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.service.UtilityService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mockito
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
    fun generateIdLengthTest(){
        Assertions.assertEquals(9, utilityService!!.generateId().length)
    }




}
