package com.dig.goodreads.helper

import java.net.URL

interface URLWrapper {
    fun generateGetURL(urlString : String) : URL;
}