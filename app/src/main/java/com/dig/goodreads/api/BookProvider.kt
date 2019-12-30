package com.dig.goodreads.api

import android.util.Log
import com.dig.goodreads.constants.ApiGlobals
import com.dig.goodreads.constants.Env
import com.dig.goodreads.helper.ResponseConverter
import com.dig.goodreads.helper.ResponseConverter.HTML2Text
import com.dig.goodreads.model.Book
import org.jetbrains.anko.doAsync
import java.net.URL


object BookProvider {

    val TAG ="BookProvider"

    fun testFetch(callback: (_ : Book) -> Unit){
       // val book = Book("test","test")
       // callback(book);
    }

    fun searchBooks(searchString : String,page : Int = 1,callback: (_ : ArrayList<Book>) -> Unit){
        //val book = Book("test","test")
        val url = URL("${ApiGlobals.GOOD_READS_HOME}search/index.xml?key=${Env.GOOD_READS_KEY}&q=$searchString&page=$page")
        //callback(book);
        Log.d(TAG, "URL : " +  url.toString())

        doAsync {
            try{

                var response = url.readText()
                var json =  ResponseConverter.XMLtoJSON(response)

                val books = ArrayList<Book>()

                var responseBooks = json?.getJSONObject("GoodreadsResponse")!!
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

                    books.add(Book(id,name,imageUrl,authorId,imageUrlLarge))
                }

                callback(books)
               // Log.d(TAG,  books.toString())

            }catch (e : Exception){
                e.printStackTrace()
            }
        }

    }

    fun getBookDescription(id : Int,callback: (description : String) -> Unit){

        val url = URL("${ApiGlobals.GOOD_READS_HOME}book/isbn/$id?key=${Env.GOOD_READS_KEY}")

        Log.d(TAG, "URL : getBook :  ${url.toString()}")

        doAsync {

            try{
                var response = url.readText()
                var json =  ResponseConverter.XMLtoJSON(response)

                var responseBookDescription = json?.getJSONObject("GoodreadsResponse")!!
                    .getJSONObject("book")
                    .getString("description")

                Log.d(TAG, "URL : BookDescription :  $responseBookDescription")

                //val escaped: String =

                callback(HTML2Text(responseBookDescription))

            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }

}