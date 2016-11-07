package com.outbrain.amplify.api.v2

import com.outbrain.amplify.api.data.*
import com.outbrain.amplify.api.helpers.Connector
import java.util.*

object AmplifyApiFactory {
    private val apiUrl = "https://api.outbrain.com/amplify/v0.1"
    private val apiaryUrl = "https://private-anon-44e44985a6-amplifyv01.apiary-mock.com"
    fun api(token: String): ApiV2.apiObject = ApiV2(Connector(token, apiUrl, null)).apiObject()
    fun apiary(token: String): ApiV2.apiObject = ApiV2(Connector(token, apiaryUrl, null)).apiObject()
    fun login(user: String, password: String): Token = login(user, password, apiUrl)
    fun demoLogin(user: String, password: String): Token = login(user, password, apiaryUrl)

    private fun login(user: String, password: String, url: String): Token =
            Connector(null, url,
                    "Basic ${Base64.getEncoder().encodeToString("$user:$password".toByteArray())}").get("/login")
}

class ApiV2 internal constructor(protected val connector: Connector) {

    open class ApiBase(protected val path: String)

    inner class apiObject {
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
        operator fun minus(create: CREATE) = OngoingRest_marketers_id_budgets_create(path)
    }

    inner class OngoingRest_marketers_id_budgets_create(path: String): ApiBase(path) {
        operator fun minus(createBudget: CreateBudget) : BudgetsList = connector.post(path, createBudget)
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
