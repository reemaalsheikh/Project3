package com.example.project3.Controller;

import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/get")
    public ResponseEntity getAllAccount (){
        return ResponseEntity.status(200).body(accountService.findAllAccounts());
    }

    @PostMapping("/create")
    public ResponseEntity createNewAccount (@AuthenticationPrincipal User user ,@Valid @RequestBody Account account){
        accountService.createNewAccount( account,user.getId());
        return ResponseEntity.status(200).body("Account created successfully");
    }

    @PutMapping("/update/{account_id}")
    public ResponseEntity updateAccount (@AuthenticationPrincipal User user ,@PathVariable Integer account_id ,@Valid @RequestBody Account account){
        accountService.updateAccount(account,account_id,user.getId());
        return ResponseEntity.status(200).body("Account updated successfully");
    }

    @DeleteMapping("/delete/{account_id}")
    public ResponseEntity deleteAccount (@AuthenticationPrincipal User user ,@PathVariable Integer account_id){
        accountService.deleteAccount(account_id,user.getId());
        return ResponseEntity.status(200).body("Account deleted successfully");
    }

    @PutMapping("/activate/{account_id}")
    public ResponseEntity activateAccount (@AuthenticationPrincipal User user , @PathVariable Integer account_id){
        accountService.activateAccount(user.getId(),account_id);
        return ResponseEntity.status(200).body("Account activated successfully");
    }


    @GetMapping("/details/{account_id}")
    public ResponseEntity viewAccountDetails(@AuthenticationPrincipal User user ,@PathVariable Integer account_id) {
        return ResponseEntity.ok(accountService.viewAccountDetails(user.getId(),account_id));
    }

    @GetMapping("/get-c-accounts")
    public ResponseEntity getCustomerAccounts (@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getCustomerAccounts(user.getId()));
    }

    @PutMapping("/deposit/{account_id}/{amount}")
    public ResponseEntity depositMoney (@AuthenticationPrincipal User user, @PathVariable Integer account_id , @PathVariable double amount ){
        accountService.depositMoney(user.getId(),account_id,amount);
        return ResponseEntity.status(200).body("Deposit money successfully");
    }

    @PutMapping("/withdraw/{account_id}/{amount}")
    public ResponseEntity withdrawMoney (@AuthenticationPrincipal User user, @PathVariable Integer account_id , @PathVariable double amount ){
        accountService.withdrawMoney(user.getId(),account_id,amount);
        return ResponseEntity.status(200).body("Withdraw money successfully");
    }

    @PutMapping("/transferFunds/{fromAccount_id}/{toAccount_id}/{amount}")
    public ResponseEntity transferFunds (@AuthenticationPrincipal User user , @PathVariable Integer fromAccount_id, @PathVariable Integer toAccount_id, @PathVariable double amount){
        accountService.transferFunds(user.getId(),fromAccount_id,toAccount_id,amount);
        return ResponseEntity.status(200).body("Transfer funds successfully");
    }

    @PutMapping("/blockAccount/{account_id}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user,@PathVariable Integer account_id){
        accountService.blockAccount(user.getId(),account_id);
        return ResponseEntity.status(200).body("Account blocked Successfully");
    }




}
