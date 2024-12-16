package com.protosstechnology.atmworkshop.service

import com.protosstechnology.atmworkshop.entity.Account
import com.protosstechnology.atmworkshop.entity.AccountRequest
import com.protosstechnology.atmworkshop.entity.AccountResponse
import com.protosstechnology.atmworkshop.entity.DeleteAccountResponse
import com.protosstechnology.atmworkshop.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import kotlin.random.Random

@Service
class AccountService{

    private val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

    @Autowired
    lateinit var accountRepository: AccountRepository

//    function to generation accountNumber randomly
    fun generateAccountNo(): Long {
        val random = Random(System.currentTimeMillis())
        return random.nextLong(1000000000, 9999999999)
    }

//    fun createAccount(@RequestBody accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
//        if (accountRequest.age <= 10) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
//        }
//
//        if (accountRequest.amount < 1000) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
//        }
//
//        val accountNo = generateAccountNo()
//        val account = Account(
//            accountNo = accountNo,
//            firstName = accountRequest.firstName,
//            lastName = accountRequest.lastName,
//            gender = accountRequest.gender,
//            age = accountRequest.age,
//            tel = accountRequest.tel,
//            amount = accountRequest.amount
//        )
//        accountRepository.save(account)
//
//        val responseBody = AccountResponse(
//            accountNo,
//            firstName = accountRequest.firstName,
//            lastName = accountRequest.lastName
//        )
//        return ResponseEntity(responseBody, HttpStatus.OK)
//    }

//    function to delete account by input accountNumber
    fun deleteAccount(no: Long): ResponseEntity<DeleteAccountResponse> {
        return try {
            val account = accountRepository.findByAccountNoEquals(no)
            if (account == null) {
                logger.warn("Account with accountNo: $no not found.")
                return ResponseEntity.notFound().build()
            }

            accountRepository.delete(account)
            val status = !accountRepository.existsById(account.id)

            val response = DeleteAccountResponse(
                status = status,
                accountNo = account.accountNo
            )

            ResponseEntity.ok(response)
        } catch (e: Exception) {
            // Log the exception
            logger.error("Error deleting account with accountNo: $no", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

//    function to get  each account detail by input accountNumber
    fun getAccountDetail(accountNo: Long): ResponseEntity<Account> {
        val account = accountRepository.findByAccountNoEquals(accountNo)
        return ResponseEntity.ok(account)
    }

//    function display all existing account
    fun getAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }
}
