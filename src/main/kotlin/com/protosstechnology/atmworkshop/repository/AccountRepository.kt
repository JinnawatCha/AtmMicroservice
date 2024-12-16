package com.protosstechnology.atmworkshop.repository

import com.protosstechnology.atmworkshop.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByFirstNameEquals(firstName: String): Account
    fun findByAccountNoEquals(accountNo: Long): Account?
    fun deleteAccountByAccountNo(accountNo: Long): Long
}
