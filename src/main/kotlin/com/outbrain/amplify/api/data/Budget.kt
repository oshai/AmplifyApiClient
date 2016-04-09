package com.outbrain.amplify.api.data


data class Budget(val id: String,
                  val name: String,
                  val shared: Boolean,
                  val currency: String,
                  val amount: Double,
                  val amountRemaining: Double,
                  val amountSpent: Double,
                  val maximumAmount: Double,
                  val creationTime: String,
                  val lastModified: String,
                  val startDate: String,
                  val endDate: String,
                  val runForever: Boolean,
                  val type: BudgetType,
                  val pacing: PacingType,
                  val dailyTarget: Double
                  )

data class UpdateBudget(val amount: Double? = null,
                        val startDate: String? = null,
                        val endDate: String? = null,
                        val runForever: Boolean? = null,
                        val dailyTarget: Double? = null)

data class CreateBudget(val name: String,
                        val amount: Double,
                        val startDate: String,
                        val endDate: String? = null,
                        val runForever: Boolean? = null,
                        val type: BudgetType,
                        val pacing: PacingType,
                        val dailyTarget: Double? = null)

data class BudgetsList(val count: Int, val budgets: List<Budget>)

enum class BudgetType { CAMPAIGN, MONTHLY, DAILY}

enum class PacingType { SPEND_ASAP, AUTOMATIC, DAILY_TARGET}
