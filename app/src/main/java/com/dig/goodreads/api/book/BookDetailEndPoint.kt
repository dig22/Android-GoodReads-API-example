package com.dig.goodreads.api.book

import com.dig.goodreads.constants.ApiGlobals
import com.dig.goodreads.constants.Env
import com.dig.goodreads.helper.ResponseConverter
import com.dig.goodreads.helper.ResponseConverter.HTML2Text
import com.dig.goodreads.model.Book
import com.github.kittinunf.fuel.httpGet

class BookDetailEndPoint {

    val BOOK_DETAILS_API = "${ApiGlobals.GOOD_READS_HOME}book/isbn/"

    fun getDescription (id : Int,callback: Callback){

        val url : String = "$BOOK_DETAILS_API$id?key=${Env.GOOD_READS_KEY}"

        url.httpGet().responseString { request, response, result ->

            var data =  ResponseConverter.XMLtoJSON(result.get())

            var responseBookDescription = data?.getJSONObject("GoodreadsResponse")!!
                    .getJSONObject("book")
                    .getString("description")

            callback.onFetchSuccess(HTML2Text(responseBookDescription))

        }
    }

    interface Callback{
        fun onFetchSuccess(description : String)
        fun onFetchFailed()
    }
}