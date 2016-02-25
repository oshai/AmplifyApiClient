package com.outbrain

fun main(args : Array<String>) {
    println("Hello, world!")
    val api = AmplifyApiClient()
    println(api.marketers().id("id").get().name)
}
