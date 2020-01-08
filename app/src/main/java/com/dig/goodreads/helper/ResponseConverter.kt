package com.dig.goodReads.helper

import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONObject
import org.jsoup.Jsoup

object ResponseConverter {
   fun xmlToJson(xml : String) : JSONObject?{
       val xmlToJson = XmlToJson.Builder(xml).build()
       return xmlToJson.toJson()
    }

    fun htmlToText(html: String): String {
        return Jsoup.parse(html).text()
    }
}