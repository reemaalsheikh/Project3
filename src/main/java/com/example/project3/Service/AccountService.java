package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

   // Create a new bank account
    public void createNewAccount(Account account , Integer customer_id) {
      Customer customer = customerRepository.findCustomerById(customer_id);
       if(customer == null){
           throw  new ApiException("Customer not found");
       }

       account.setAccountNumber(account.getAccountNumber());
       account.setBalance(account.getBalance());


        account.setCustomer(customer);
       accountRepository.save(account);
    }

    public void updateAccount(Account account , Integer customer_id , Integer account_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(account_id);
        if(oldAccount == null){
            throw  new ApiException("Account not found");
        }else if(oldAccount.getCustomer().getId() != customer_id){
            throw  new ApiException("Customer id mismatch, u don't have authority");
        }
        oldAccount.setBalance(account.getBalance());
        oldAccount.setCustomer(customer);
        accountRepository.save(oldAccount);

    }

    public void deleteAccount( Integer customer_id , Integer account_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(account_id);
        if(oldAccount == null){
            throw  new ApiException("Account not found");
        }else if(oldAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        accountRepository.delete(oldAccount);
        }



    //Active a bank account
    public void activateAccount (Integer customer_id, Integer account_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(account_id);
        if(oldAccount == null){
            throw  new ApiException("Account not found");
        }else if(oldAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        oldAccount.setActive(true);
        accountRepository.save(oldAccount);
    }


    //View account details
    public Account viewAccountDetails(Integer customer_id,Integer account_id) {
        Account account = accountRepository.findAccountById(account_id);
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");
        }else if(account.getCustomer().getId()!=customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        return account;

    }


    //List user's accounts
    public List<Account> getCustomerAccounts(Integer customer_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        return accountRepository.findAllByCustomer(customer);
    }


    //Deposit and withdraw money
    public Account depositMoney(Integer account_id,Integer customer_id,double amount) {
        Account account = accountRepository.findAccountById(account_id);
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");
        }else if(account.getCustomer().getId()!= customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }


    public Account withdrawMoney(Integer account_id, Integer customer_id,double amount) {
        Account account = accountRepository.findAccountById(account_id);
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");

        }else if(account.getCustomer().getId()!= customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }


    //Transfer funds between accounts
    public void transferFunds(Integer fromAccount_id, Integer toAccount_id, Integer customer_id,double amount) {
        Account fromAccount = accountRepository.findAccountById(fromAccount_id);
        Account toAccount = accountRepository.findAccountById(toAccount_id);
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        if (fromAccount == null || toAccount == null) {
            throw new ApiException("Account not found");
        }else if(fromAccount.getCustomer().getId()!= customer_id || toAccount.getCustomer().getId()!=customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //Block bank account

    public void blockAccount (Integer customer_id, Integer account_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw  new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(account_id);
        if(oldAccount == null){
            throw  new ApiException("Account not found");
        }else if(oldAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        oldAccount.setActive(false);
        accountRepository.save(oldAccount);
    }





}
