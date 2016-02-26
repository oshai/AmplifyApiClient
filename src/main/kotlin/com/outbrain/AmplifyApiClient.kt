package com.outbrain

import com.google.gson.Gson
import java.awt.PageAttributes
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class AmplifyApiClient(token: String): ApiBase("", Connector(token)) {

    fun marketers(): Marketers {
        return Marketers(connector)
    }

}

class Connector(token: String) {
    private fun URL_START() = "http://private-anon-d88a09c6c-amplifyv01.apiary-mock.com"
    fun get(url: String): Marketer {
//        val html: String = JdkRequest(URL_START())
//                .uri().path(url).queryParam("id", 333).back()
//                .method(Request.GET)
//                //.header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
//                .fetch()
//                .`as`(RestResponse::class.java)
////                        .assertStatus(HttpURLConnection.HTTP_OK)
//                        .body();

        val obj: URL = URL(URL_START() + url);
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection;

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);

        val responseCode: Int = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        val inBuffer : BufferedReader= BufferedReader(
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
        val result = Gson().fromJson(response.toString(), Marketer::class.java)
        return result
    }
}


open class ApiBase(protected val url: String, protected val connector: Connector) {

}

class Marketers(connector: Connector): ApiBase("/marketers", connector) {
    fun id(id: String): MarketerId {
        return MarketerId(id, connector)
    }
}

class MarketerId(id: String, connector: Connector): ApiBase("/marketers/$id", connector) {
    fun get(): Marketer {
        return connector.get(url)
    }

}

data class Marketer(val name: String) {

}


