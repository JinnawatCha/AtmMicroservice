package com.protosstechnology.atmworkshop.controller

import com.protosstechnology.atmworkshop.entity.*
import com.protosstechnology.atmworkshop.repository.AccountRepository
import com.protosstechnology.atmworkshop.service.AccountService
import com.protosstechnology.atmworkshop.service.BankingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ATMController {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var bankingService: BankingService

    @PostMapping("/getAccountDetail/{no}")
    fun getAccountDetail(@PathVariable no: Long): ResponseEntity<ResponseEntity<Account>> {
        return ResponseEntity.ok().body(accountService.getAccountDetail(no))
    }

    @PostMapping("/createAccount")
    fun createAccount(@RequestBody accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
        if (accountRequest.age <= 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        if (accountRequest.amount < 1000) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        val accountNo = accountService.generateAccountNo()
        val account = Account(
            accountNo = accountNo,
            firstName = accountRequest.firstName,
            lastName = accountRequest.lastName,
            gender = accountRequest.gender,
            age = accountRequest.age,
            tel = accountRequest.tel,
            amount = accountRequest.amount
        )
        accountRepository.save(account)

        val responseBody = AccountResponse(
            accountNo,
            firstName = accountRequest.firstName,
            lastName = accountRequest.lastName
        )
        return ResponseEntity(responseBody, HttpStatus.OK)
    }

    @PutMapping("/withdraw/{accountNo}/{amount}")
    fun withdraw(@PathVariable accountNo: Long, @PathVariable amount: Double): ResponseEntity<Account> {
        return bankingService.withdrawDeposit("withdraw", accountNo, amount)
    }

    @PutMapping("/deposit/{accountNo}/{amount}")
    fun deposit(@PathVariable accountNo: Long, @PathVariable amount: Double): ResponseEntity<Account> {
        return bankingService.withdrawDeposit("deposit", accountNo, amount)
    }

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<List<Account>> {
        val allAccount = accountService.getAllAccounts()
        return ResponseEntity.ok().body(allAccount)
    }

    @GetMapping("/checkBalance/{no}")
    fun checkBalance(@PathVariable no: Long): ResponseEntity<BalanceResponse> {
        return bankingService.checkBalance(no)
    }

    @PostMapping("/deleteAccount/{no}")
    fun deleteAccount(@PathVariable no: Long): ResponseEntity<DeleteAccountResponse>{
        return accountService.deleteAccount(no)
    }
}