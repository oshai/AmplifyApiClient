package com.outbrain.amplify.api.data


data class Budget(val id: String,
                  val name: String,
                  val shared: Boolean,
                  val currency: String,
                  val amount: Float,
                  val amountRemaining: Float,
                  val amountSpent: Float,
                  val maximumAmount: Float,
                  val creationTime: String,
                  val lastModified: String,
                  val startDate: String,
                  val endDate: String,
                  val runForever: Boolean,
                  val type: String,
                  val pacing: String,
                  val dailyTarget: Float
                  )

data class UpdateBudget(val amount: Float? = null,
                        val startDate: String? = null,
                        val endDate: String? = null,
                        val runForever: Boolean? = null,
                        val dailyTarget: Float? = null)

data class CreateBudget(val name: String,
                        val amount: Float,
                        val startDate: String,
                        val endDate: String? = null,
                        val runForever: Boolean? = null,
                        val type: String,
                        val pacing: String,
                        val dailyTarget: Float? = null)

data class BudgetsList(val count: Int, val budgets: List<Budget>)
