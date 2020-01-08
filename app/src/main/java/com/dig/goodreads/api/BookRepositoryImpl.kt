package com.dig.goodReads.api

import com.dig.goodReads.BuildConfig
import com.dig.goodReads.api.model.Book
import com.dig.goodReads.components.books.BooksState
import com.dig.goodReads.components.details.DetailsState
import com.dig.goodReads.helper.ResponseConverter
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//val TAG ="BookProvider"
const val GOOD_READS_HOME = "https://www.goodreads.com/"
const val BOOK_DETAILS_API = "${GOOD_READS_HOME}book/isbn/"
const val SEARCH_API = "${GOOD_READS_HOME}search/index.xml?key=${BuildConfig.GOOD_READS_KEY}"

class BookRepositoryImpl
            constructor(private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default)
            : BookRepository{

    override suspend fun searchBooksNew(searchString : String,page : Int): BooksState = withContext(backgroundDispatcher){
        val url  = "$SEARCH_API&q=$searchString&page=$page"
        val (request, response, result) = Fuel.get(url).awaitStringResponseResult()

        result.fold<BooksState>(
            { data ->

                val resData =  ResponseConverter.xmlToJson(result.get())

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

                    books.add(
                        Book(
                            id,
                            name,
                            imageUrl,
                            authorId,
                            authorName,
                            imageUrlLarge
                        )
                    )
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
                val resultData =  ResponseConverter.xmlToJson(result.get())
                val responseBookDescription = resultData?.getJSONObject("GoodreadsResponse")!!
                        .getJSONObject("book")
                        .getString("description")

                return@withContext DetailsState.DetailsLoaded(ResponseConverter.htmlToText(responseBookDescription))
                //return@withContext DetailsState.Error("")
            },
            { error ->
                return@withContext DetailsState.Error("")
            }
        )
    }

}

