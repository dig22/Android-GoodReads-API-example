package com.dig.goodReads.data

import com.dig.goodReads.BOOK_DETAILS_API
import com.dig.goodReads.BuildConfig
import com.dig.goodReads.SEARCH_API
import com.dig.goodReads.model.Book
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.bookDetails.BookDetailsState
import com.dig.goodReads.helper.ResponseConverter
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import java.lang.Exception

class BookRepositoryImpl : BookRepository, KoinComponent{

    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default

    override suspend fun searchBooks(searchString : String, page : Int): BooksState = withContext(backgroundDispatcher) {
        val url = "$SEARCH_API&q=$searchString&page=$page"
        try {
            val result = Fuel.get(url).awaitString()
            val resData = ResponseConverter.xmlToJson(result)
            val books = ArrayList<Book>()

            val responseBooks =
                resData?.getJSONObject("GoodreadsResponse")!!
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

                books.add(Book(id, name, imageUrl, authorId, authorName, imageUrlLarge))
            }
            return@withContext BooksState.BooksLoaded(books)
        } catch (exception: Exception) {
            return@withContext BooksState.Error("")
        }
    }

    override suspend fun getBookDescription(bookId : Int) : BookDetailsState = withContext(backgroundDispatcher){
        val url = "$BOOK_DETAILS_API$bookId?key=${BuildConfig.GOOD_READS_KEY}"
        try{
            val result = Fuel.get(url).awaitString()
            val resultData =  ResponseConverter.xmlToJson(result)
            val responseBookDescription = resultData?.getJSONObject("GoodreadsResponse")!!
                    .getJSONObject("book")
                    .getString("description")
            return@withContext BookDetailsState.BookDetailsLoaded(ResponseConverter.htmlToText(responseBookDescription))
        }catch (e : Exception){
            return@withContext BookDetailsState.Error("")
        }
    }
}