package com.delegation.horizon.service

import org.json.simple.JSONArray
import org.springframework.stereotype.Service
import org.json.simple.JSONObject
import java.io.FileWriter
import java.io.IOException

@Service
class UtilityService {

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
}