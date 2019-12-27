package com.dig.goodreads.api

import android.util.Log
import com.dig.goodreads.constants.ApiGlobals
import com.dig.goodreads.constants.Env
import com.dig.goodreads.model.Book
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

const val TAG ="BookProvider"
object BookProvider {

    fun testFetch(callback: (_ : Book) -> Unit){
        val book = Book("test","test")
        callback(book);
    }

    fun searchBooks(searchString : String,callback: (_ : Array<Book>) -> Unit){
        val book = Book("test","test")
        val url = URL("${ApiGlobals.GOOD_READS_HOME}search/index.xml?key=${Env.GOOD_READS_KEY}&q=$searchString")
        //callback(book);
        Log.d(TAG, "URL : " +  url.toString())

        doAsync {
            try{
                var response = url.readText()

                Log.d(TAG,  JSONObject(response).toString())


            }catch (e : Exception){
                e.printStackTrace()
            }
        }

    }

}