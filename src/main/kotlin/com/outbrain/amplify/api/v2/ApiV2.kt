package com.outbrain.amplify.api.v2

import com.outbrain.amplify.api.data.*
import com.outbrain.amplify.api.helpers.Connector

class ApiV2(token: String) {
    private val connector = Connector(token)

    open class ApiBase(protected val path: String)

    inner class apiari {
        operator fun div(m: marketers) = OngoingRest_marketers()
        operator fun div(b: budgets) = OngoingRest_budgets()
    }

    inner class OngoingRest_marketers: ApiBase("/marketers") {
        operator fun div(id: String) = OngoingRest_marketers_id(path, id)
        operator fun minus(get: GET) : MarketersList = connector.get(path)
    }

    inner class OngoingRest_marketers_id(parentPath: String, id: String): ApiBase("$parentPath/$id") {
        operator fun minus(get: GET) : Marketer = connector.get(path)
        operator fun minus(update: UPDATE) = OngoingRest_marketers_id_update(path)
        operator fun div(b: budgets) = OngoingRest_marketers_id_budgets(path)
    }
    inner class OngoingRest_marketers_id_update(path: String): ApiBase(path) {
        operator fun minus(updateMarketer: UpdateMarketer) : Marketer = connector.put(path, updateMarketer)
    }
    inner class OngoingRest_marketers_id_budgets(path: String): ApiBase("$path/budgets") {
        operator fun minus(get: GET) : BudgetsList = connector.get(path)
    }

    inner class OngoingRest_budgets: ApiBase("/budgets") {
        operator fun div(id: String) = OngoingRest_budgets_id(path, id)
    }
    inner class OngoingRest_budgets_id(parentPath: String, id: String): ApiBase("$parentPath/$id") {
        operator fun minus(get: GET) : Budget = connector.get(path)
        operator fun minus(update: UPDATE) = OngoingRest_budgets_id_update(path)
    }
    inner class OngoingRest_budgets_id_update(path: String): ApiBase(path) {
        operator fun minus(updateBudget: UpdateBudget) : Budget = connector.put(path, updateBudget)
    }
}
object marketers
object budgets

object GET
object UPDATE
object CREATE
object DELETE
