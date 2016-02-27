package com.outbrain.amplify.api.helpers

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Connector(val token: String) {
    companion object: WithLogging() {
        val URL_START = "http://private-anon-d88a09c6c-amplifyv01.apiary-mock.com"
    }
    inline fun <reified T : Any> get(url: String): T {
        val obj: URL = URL(URL_START + url);
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection;
        // optional default is GET
        con.requestMethod = "GET";
        //add request header
        con.setRequestProperty("OB-TOKEN-V1", token);

        val responseCode: Int = con.responseCode;
        logger.debug("Sending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        val inBuffer : BufferedReader = BufferedReader(
                InputStreamReader(con.inputStream));
        var inputLine: String?;
        val response: StringBuffer = StringBuffer();

        inputLine = inBuffer.readLine()
        while ((inputLine) != null) {
            response.append(inputLine);
            inputLine = inBuffer.readLine()
        }
        inBuffer.close();

        logger.debug("response: " + response.toString());
        val result = GsonBuilder().create().fromJson<T>(response.toString())
        return result
    }
}