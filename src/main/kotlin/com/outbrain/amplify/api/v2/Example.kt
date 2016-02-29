package com.outbrain.amplify.api.v2

import com.outbrain.amplify.api.data.BlockedPublisher
import com.outbrain.amplify.api.data.UpdateMarketer


fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    val token = "42"
    val api = ApiV2(token).apiari()

    println(api/marketers/"id_1" - GET)
    println(api/marketers/"id_1" - UPDATE - UpdateMarketer(listOf(BlockedPublisher("1"))))

}
