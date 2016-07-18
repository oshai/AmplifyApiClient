package com.outbrain.amplify.api

import com.outbrain.amplify.api.data.BlockedPublisher
import com.outbrain.amplify.api.data.UpdateMarketer
import mu.KLogger
import mu.KLogging

object Log : KLogging()
fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    Log.logger.info { "starting" }
    val token = "42"
    val api = AmplifyApiClient(token)
    println(api.marketers().id("id_1").get())
    println(api.marketers().id("id_1").update(UpdateMarketer(listOf(BlockedPublisher("1")))))
    Log.logger.info { "finished" }
}
