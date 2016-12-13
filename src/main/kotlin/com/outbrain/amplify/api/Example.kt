package com.outbrain.amplify.api

import com.outbrain.amplify.api.data.BlockedPublisher
import com.outbrain.amplify.api.data.UpdateMarketer
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    logger.info { "starting" }
    val token = "42"
    val api = AmplifyApiClient(token)
    println(api.marketers().id("id_1").get())
    println(api.marketers().id("id_1").update(UpdateMarketer(listOf(BlockedPublisher("1")))))
    logger.info { "finished" }
}
