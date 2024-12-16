package com.protosstechnology.atmworkshop.entity

data class AccountRequest(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val age: Int,
    val tel: String,
    val amount: Double
)
