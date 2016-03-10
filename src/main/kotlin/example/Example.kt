package example

import com.outbrain.amplify.api.data.*
import com.outbrain.amplify.api.v2.*


fun main(args : Array<String>) {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug")
    val token = "42"
    val api = AmplifyApiFactory.apiary(token)
    println(listOf(
            api/ marketers - GET,
            api/ marketers /"marketer_id" - GET,
            api/ marketers /"marketer_id" - UPDATE - UpdateMarketer(listOf(BlockedPublisher("1"))),
            api/ marketers /"marketer_id"/ budgets - GET,
            api/ marketers /"marketer_id"/ budgets - CREATE - CreateBudget(name = "new budget",
                    amount = 1F,
                    startDate = "2014-01-15",
                    runForever = true,
                    type = "MONTHLY",
                    pacing = "AUTOMATIC"),

            api/ budgets /"budget_id" - GET,
            api/ budgets /"budget_id" - UPDATE - UpdateBudget(amount = 100F)
    ))

}
