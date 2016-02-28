package com.outbrain.amplify.api.helpers

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import com.outbrain.amplify.api.AmplifyApiException
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Connector(val token: String) {
    companion object: WithLogging() {
        val URL_START = "http://private-anon-d88a09c6c-amplifyv01.apiary-mock.com"
    }
    inline fun <reified T : Any> get(url: String): T {
        val con: HttpURLConnection = getConnection(url)
        return consumeResponse(con)
    }

    inline fun <reified T : Any> consumeResponse(con: HttpURLConnection): T {
        val responseCode: Int = con.responseCode;
        logger.debug("Response Code : " + responseCode);
        val inBuffer: BufferedReader = BufferedReader(
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
        if (responseCode < 200 || responseCode > 299) {
            throw AmplifyApiException("http request failed, status '$responseCode' reposnse: '${response.toString()}'")
        }
        val result = GsonBuilder().create().fromJson<T>(response.toString())
        return result
    }

    inline fun getConnection(url: String): HttpURLConnection {
        val obj: URL = URL(URL_START + url);
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection;
        logger.debug("Sending request to URL : $URL_START$url")
        con.setRequestProperty("OB-TOKEN-V1", token);
        return con
    }

    inline fun <reified T : Any, D> put(url: String, data: D): T {
        val con: HttpURLConnection = getConnection(url)
        con.requestMethod = "PUT";
        logger.debug("Sending 'PUT' request")
//        con.setRequestProperty("User-Agent", USER_AGENT);
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        val urlParameters: String = GsonBuilder().setPrettyPrinting().create().toJson(data)
        logger.debug("body is $urlParameters")
        con.doOutput = true;
        val wr: DataOutputStream = DataOutputStream(con.outputStream);
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        return consumeResponse(con)
    }
}