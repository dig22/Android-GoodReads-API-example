package com.dig.goodreads.api.book

import com.dig.goodreads.BuildConfig
import com.dig.goodreads.constant.ApiGlobals
import com.dig.goodreads.helper.ResponseConverter
import com.dig.goodreads.model.Book
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

open class BookSearchEndPoint {

    val SEARCH_API = "${ApiGlobals.GOOD_READS_HOME}search/index.xml?key=${BuildConfig.GOOD_READS_KEY}"

    open fun searchBooks (searchString : String,page : Int = 1,callback: Callback?){

        val url : String = "$SEARCH_API&q=$searchString&page=$page"
        //Log.d(TAG, "URL : " +  url.toString())

        url.httpGet().responseString { request, response, result ->

            when (result) {

                is Result.Failure -> {
                   callback?.onFetchFailed("Error")
                }
                is Result.Success -> {
                    var data =  ResponseConverter.XMLtoJSON(result.get())

                    val books = ArrayList<Book>()

                    var responseBooks = data?.getJSONObject("GoodreadsResponse")!!
                        .getJSONObject("search")
                        .getJSONObject("results")
                        .getJSONArray("work")



                    for (i in 0 until responseBooks.length()) {
                        val item = responseBooks.getJSONObject(i)

                        val book = item.getJSONObject("best_book")
                        val id = book.getJSONObject("id").getInt("content")
                        val name = book.getString("title")
                        val imageUrl = book.getString("small_image_url")
                        val imageUrlLarge = book.getString("image_url")

                        val authorId = book.getJSONObject("author")
                            .getJSONObject("id").getInt("content")
                        val authorName = book.getJSONObject("author")
                            .getString("name")

                        books.add(Book(id,name,imageUrl,authorId,authorName,imageUrlLarge))
                    }

                    callback?.onFetchSuccess(books)
                }

            }
        }

    }

    interface Callback{
        fun onFetchSuccess(books: List<Book>)
        fun onFetchFailed(error : String)
    }
}