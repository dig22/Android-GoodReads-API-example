package com.dig.goodreads.helper

import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONObject

object ResponseConverter {
   fun XMLtoJSON(xml : String) : JSONObject?{
       val xmlToJson = XmlToJson.Builder(xml).build()
       return xmlToJson.toJson();
    }
}