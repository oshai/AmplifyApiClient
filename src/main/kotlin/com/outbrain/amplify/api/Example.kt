package com.outbrain.amplify.api

fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    val token = "42"
    val api = AmplifyApiClient(token)
    println(api.marketers().id("id").get())
}
