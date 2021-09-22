package com.delegation.horizon.service

import org.json.simple.JSONArray
import org.springframework.stereotype.Service
import org.json.simple.JSONObject
import java.io.FileWriter
import java.io.IOException

@Service
class UtilityService {

    fun generateJSONFile(templateName: String) : JSONObject{
        var jsonObject: JSONObject = JSONObject()

        jsonObject.put("Name","Horizon")
        jsonObject.put("API","www.horizonIndia.com")

        var list: JSONArray = JSONArray()
        list.add("Shiv")
        list.add("Satya")

        jsonObject.put("Devs", list)

        try{
            var fileWriter: FileWriter =  FileWriter(templateName+".json")
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