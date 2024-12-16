package com.protosstechnology.atmworkshop.service

import com.protosstechnology.atmworkshop.entity.Account
import com.protosstechnology.atmworkshop.entity.BalanceResponse
import com.protosstechnology.atmworkshop.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BankingService {

    @Autowired
    lateinit var accountRepository: AccountRepository

//    function to do withdraw or deposit by select method inside
    fun withdrawDeposit(method: String, accountNo: Long, newAmount: Double): ResponseEntity<Account> {
        return try {
            val account = accountRepository.findByAccountNoEquals(accountNo)
                ?: return ResponseEntity.notFound().build()

            when (method) {
                "withdraw" -> {
                    if (newAmount <= 0) {
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
                    } else if (account.amount >= newAmount) {
                        account.amount -= newAmount
                        accountRepository.save(account) // Save the updated account
                        ResponseEntity.ok(account)
                    } else {
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
                    }
                }
                "deposit" -> {
                    if (newAmount <= 0) {
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
                    } else {
                        account.amount += newAmount
                        accountRepository.save(account) // Save the updated account
                        ResponseEntity.ok(account)
                    }
                }
                else -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

//    function to get balance amount by input accountNumber
    fun checkBalance(no: Long): ResponseEntity<BalanceResponse> {
        return try {
            val account = accountRepository.findByAccountNoEquals(no)
                ?: return ResponseEntity.notFound().build()
            val response = BalanceResponse(
                amount = account.amount.toString()+" บาท",
                accountNo = account.accountNo
            )
            return ResponseEntity.ok(response)
        }
        catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}