package com.outbrain.amplify.api.helpers

import com.google.gson.GsonBuilder
import com.outbrain.amplify.api.data.Marketer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.github.salomonbrys.kotson.fromJson


class Connector(token: String) {
    companion object {
        val URL_START = "http://private-anon-d88a09c6c-amplifyv01.apiary-mock.com"
    }
    inline fun <reified T : Any> get(url: String): T {

        //        val html: String = JdkRequest(URL_START())
        //                .uri().path(url).queryParam("id", 333).back()
        //                .method(Request.GET)
        //                //.header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
        //                .fetch()
        //                .`as`(RestResponse::class.java)
        ////                        .assertStatus(HttpURLConnection.HTTP_OK)
        //                        .body();

        val obj: URL = URL(URL_START + url);
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection;

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        //        con.setRequestProperty("User-Agent", USER_AGENT);

        val responseCode: Int = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        val inBuffer : BufferedReader = BufferedReader(
                InputStreamReader(con.getInputStream()));
        var inputLine: String?;
        val response: StringBuffer = StringBuffer();

        inputLine = inBuffer.readLine()
        while ((inputLine) != null) {
            response.append(inputLine);
            inputLine = inBuffer.readLine()
        }
        inBuffer.close();

        //print result
        System.out.println(response.toString());
        val builder = GsonBuilder()
        val gson = builder.create()
        val result = gson.fromJson<T>(response.toString())
        return result
    }
}