package com.outbrain.amplify.api.data

data class Marketer(val id: String,
                    val name: String,
                    val lastModified: String,
                    val creationTime: String,
                    val blockedPublishers: List<BlockedPublisher>,
                    val enabled: Boolean)

data class BlockedPublisher(val id: String)

data class UpdateMarketer(val blockedPublishers: List<BlockedPublisher>)

data class MarketersList(val count: Int, val marketers: List<Marketer>)