package com.protosstechnology.atmworkshop.entity

import jakarta.persistence.*

@Entity
@Table(name = "account")
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "account_number")
    val accountNo: Long,

    @Column(name = "firstName")
    val firstName: String,

    @Column(name = "lastName")
    val lastName: String,

    @Column(name = "gender")
    val gender: String,

    @Column(name = "age")
    val age: Int,

    @Column(name = "tel")
    val tel: String,

    @Column(name = "amount")
    var amount: Double
)