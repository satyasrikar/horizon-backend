package com.delegation.horizon.service

import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
import com.delegation.horizon.request.MappingRequestDTO
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.stereotype.Service
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


@Service
class UtilityService(val partnerMappingRepository: PartnerMappingRepository, val motorInsuranceService: MotorInsuranceService) {


    fun generateJSONFile(jsonRequest: JSONObject) : PartnerMapping{



        val partnerMapping = PartnerMapping()
        partnerMapping.mappingId = motorInsuranceService.generateId()
        partnerMapping.mappingContent = jsonRequest.toJSONString()
        try{
            val fileWriter =  FileWriter("partnerMapping.json")
            println("Writing to file")
            fileWriter.write(jsonRequest.toJSONString())
            fileWriter.flush()


            partnerMappingRepository.save(partnerMapping)
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
        println("JSON Object=")
        println(jsonRequest)

        return partnerMapping
    }





}