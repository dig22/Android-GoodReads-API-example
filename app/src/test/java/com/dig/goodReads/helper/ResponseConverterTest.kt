package com.dig.goodReads.helper

import org.junit.Test

class ResponseConverterTest {

    @Test
    fun xml_to_json(){
        val result = ResponseConverter.xmlToJson("<test>hi</test>")
        assert(result?.getString("test")== "hi")
    }

    @Test
    fun html_to_text(){
        val result = ResponseConverter.htmlToText("<b>hi</b>")
        assert(result == "hi")
    }
}