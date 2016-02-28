package com.outbrain.amplify.api

import com.outbrain.amplify.api.data.BlockedPublisher
import com.outbrain.amplify.api.data.UpdateMarketer

fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    val token = "42"
    val api = AmplifyApiClient(token)
    println(api.marketers().id("id_1").get())
    println(api.marketers().id("id_1").update(UpdateMarketer(arrayListOf(BlockedPublisher("1")))))
}
