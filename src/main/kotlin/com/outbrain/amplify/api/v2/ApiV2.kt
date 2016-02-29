package com.outbrain.amplify.api.v2

import com.outbrain.amplify.api.data.Marketer
import com.outbrain.amplify.api.data.UpdateMarketer
import com.outbrain.amplify.api.helpers.Connector

class ApiV2(token: String) {
    private val connector = Connector(token)

    open class ApiBase(protected val path: String)

    inner class apiari {
        operator fun div(m: marketers) = OngoingRest_marketers()
    }

    inner class OngoingRest_marketers: ApiBase("/marketers") {
        operator fun div(id: String) = OngoingRest_marketers_id(path, id)
    }

    inner class OngoingRest_marketers_id(parentPath: String, id: String): ApiBase("$parentPath/$id") {
        operator fun minus(get: GET) : Marketer = connector.get(path)
        operator fun minus(update: UPDATE) = OngoingRest_marketers_id_update(path)
    }
    inner class OngoingRest_marketers_id_update(path: String): ApiBase(path) {
        operator fun minus(updateMarketer: UpdateMarketer) : Marketer = connector.put(path, updateMarketer)
    }
}
object marketers
object GET
object UPDATE
object CREATE
object DELETE
