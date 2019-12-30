package com.dig.goodreads.helper

import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONObject
import org.jsoup.Jsoup




object ResponseConverter {
   fun XMLtoJSON(xml : String) : JSONObject?{
       val xmlToJson = XmlToJson.Builder(xml).build()
       return xmlToJson.toJson();
    }

    fun HTML2Text(html: String): String {
        return Jsoup.parse(html).text()
    }
}