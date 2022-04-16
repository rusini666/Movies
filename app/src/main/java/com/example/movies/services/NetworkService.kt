package com.example.movies.services


import java.io.BufferedReader
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NetworkService() {
    companion object{

        fun get(sUrl: String): String? {
            val inputStream: InputStream
            var result: String? = null
            try {
                // Create URL
                val url = URL(sUrl)

                // Create HttpURLConnection
                val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

                // Launch GET request
                conn.connect()

                // Receive response as inputStream
                inputStream = conn.inputStream

                if (inputStream != null) {
                    // Convert input stream to string
                    result = inputStream.bufferedReader().use(BufferedReader::readText)
                }else{
                    throw Exception("Unknown error occurred!")
                }

            }
            catch(err:Error) {
                throw Exception("Failed to fetch the data from the server!")
            }

            return result
        }
    }

}