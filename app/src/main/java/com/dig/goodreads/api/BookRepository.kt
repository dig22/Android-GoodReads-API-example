package com.dig.goodreads.api

import com.dig.goodreads.BuildConfig
import com.dig.goodreads.model.Book
import com.dig.goodreads.components.book.BooksState
import com.dig.goodreads.components.details.DetailsState
import com.dig.goodreads.constant.ApiGlobals
import com.dig.goodreads.helper.ResponseConverter
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepositoryImpl
            constructor(private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default)
            :BookRepository{

    val TAG ="BookProvider"
    val BOOK_DETAILS_API = "${ApiGlobals.GOOD_READS_HOME}book/isbn/"
    val SEARCH_API = "${ApiGlobals.GOOD_READS_HOME}search/index.xml?key=${BuildConfig.GOOD_READS_KEY}"

    override suspend fun searchBooksNew(searchString : String,page : Int): BooksState = withContext(backgroundDispatcher){
        val url : String = "$SEARCH_API&q=$searchString&page=$page"

        val (request, response, result) = Fuel.get(url).awaitStringResponseResult()

        result.fold<BooksState>(
            { data ->

                val resData =  ResponseConverter.XMLtoJSON(result.get())

                val books = ArrayList<Book>()

                val responseBooks = resData?.getJSONObject("GoodreadsResponse")!!
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


                return@withContext BooksState.BooksLoaded(books)
            },
            { error ->
                return@withContext BooksState.Error("")
            })
        }

    override suspend fun getBookDescription(bookId : Int) : DetailsState = withContext(backgroundDispatcher){


        val url = "$BOOK_DETAILS_API$bookId?key=${BuildConfig.GOOD_READS_KEY}"


        val (request, response, result) = Fuel.get(url).awaitStringResponseResult()

        result.fold<DetailsState>(
            { data -> println(data)
                val resdata =  ResponseConverter.XMLtoJSON(result.get())
                val responseBookDescription = resdata?.getJSONObject("GoodreadsResponse")!!
                        .getJSONObject("book")
                        .getString("description")

                return@withContext DetailsState.DetailsLoaded(ResponseConverter.HTML2Text(responseBookDescription))
                //return@withContext DetailsState.Error("")
            },
            { error ->
                return@withContext DetailsState.Error("")
            }
        )
    }

}

interface BookRepository {
     suspend fun getBookDescription(bookId: Int) : DetailsState
     suspend fun searchBooksNew(searchString : String,page : Int): BooksState
}