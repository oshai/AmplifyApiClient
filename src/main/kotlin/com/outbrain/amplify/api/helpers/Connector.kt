package com.outbrain.amplify.api.helpers

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import com.outbrain.amplify.api.AmplifyApiException
import mu.KLogging
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Connector(val token: String?, val urlStart: String, val authorization: String?) {
    companion object: KLogging()

    inline fun <reified T : Any> get(url: String): T {
        val con: HttpURLConnection = getConnection(url)
        return consumeResponse(con)
    }

    inline fun <reified T : Any> consumeResponse(con: HttpURLConnection): T {
        val responseCode: Int = con.responseCode
        logger.debug("Response Code : " + responseCode)
        val inBuffer: BufferedReader = BufferedReader(
                InputStreamReader(con.inputStream))
        var inputLine: String?
        val response: StringBuffer = StringBuffer()
        inBuffer.use {
            inputLine = inBuffer.readLine()
            while ((inputLine) != null) {
                response.append(inputLine);
                inputLine = inBuffer.readLine()
            }
        }
        logger.debug("response: " + response.toString())
        if (responseCode < 200 || responseCode > 299) {
            throw AmplifyApiException("http request failed, status '$responseCode' reposnse: '${response.toString()}'")
        }
        val result = GsonBuilder().create().fromJson<T>(response.toString())
        return result
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun getConnection(url: String): HttpURLConnection {
        val obj: URL = URL(urlStart + url);
        val con: HttpURLConnection = obj.openConnection() as HttpURLConnection;
        logger.debug("Sending request to URL : $urlStart$url")
        token?.let {t -> con.setRequestProperty("OB-TOKEN-V1", t) }
        authorization?.let {t -> con.setRequestProperty("Authorization", t) }
        con.setRequestProperty("Content-Type", "application/json")
        return con
    }

    inline fun <reified T : Any, D> post(url: String, data: D): T = doWithBody(url, data, "POST")
    inline fun <reified T : Any, D> put(url: String, data: D): T = doWithBody(url, data, "PUT")

    inline fun <reified T : Any, D> doWithBody(url: String, data: D, method: String): T {
        val con: HttpURLConnection = getConnection(url)
        con.requestMethod = method
        logger.debug("Sending '$method' request")
        val urlParameters: String = GsonBuilder().setPrettyPrinting().create().toJson(data)
        logger.debug("body is $urlParameters")
        con.doOutput = true;
        val wr: DataOutputStream = DataOutputStream(con.outputStream)
        wr.writeBytes(urlParameters)
        wr.flush()
        wr.close()
        return consumeResponse(con)
    }
}
