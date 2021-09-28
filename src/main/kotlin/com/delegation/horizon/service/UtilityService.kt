package com.delegation.horizon.service

import com.delegation.horizon.model.PartnerMapping
import com.delegation.horizon.repository.PartnerMappingRepository
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


    fun generateJSONFile(templateName: String) : JSONObject{
        val jsonObject = JSONObject()

        jsonObject["Name"] = "Horizon"
        jsonObject["API"] = "www.horizonIndia.com"

        val list = JSONArray()
        list.add("Shiv")
        list.add("Satya")

        jsonObject["Devs"] = list

        try{
            val fileWriter =  FileWriter("$templateName.json")
            println("Writing to file")
            fileWriter.write(jsonObject.toJSONString())
            fileWriter.flush()
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
        println("JSON Object=")
        println(jsonObject)

        return jsonObject
    }
    fun generatePropertiesFile(): String {
        val props = Properties()
        props["Device_name"] = "OnePlus7"
        props["Android_version"] = "9"
        props["Model"] = "GM1901"
        props["CPU"] = "Snapdragon855"
        //Instantiating the FileInputStream for output file
        //Instantiating the FileInputStream for output file
        val path = "src\\main\\resources\\partner.properties"
        val outputStream = FileOutputStream(path)
        //Storing the properties file
        //Storing the properties file
        props.store(outputStream, "This is a sample properties file")
        println("Properties file created......")

        return readPropertiesFile()

    }

    fun readPropertiesFile(): String {
        val path = "src\\main\\resources\\partner.properties"
//        val fr = FileReader(path)
//        val a = CharArray(500)
//       var string: String = String(a)
//        fr.read(a) // reads the content to the array
        val fileName: Path = Path.of(path)

        // Now calling Files.readString() method to
        // read the file

        // Now calling Files.readString() method to
        // read the file
        val str: String = Files.readString(fileName)


//        for (c in a) print(c) // prints the characters one by one

        println(str)

        val partnerMapping = PartnerMapping()
        partnerMapping.mappingId = motorInsuranceService.generateId()
        partnerMapping.mappingContent = str
        partnerMappingRepository.save(partnerMapping)


//        fr.close()
        return partnerMapping.mappingContent

    }
}