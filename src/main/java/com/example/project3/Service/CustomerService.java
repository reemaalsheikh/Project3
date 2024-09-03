package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }



    public void customerRegister(CustomerDTO customerDTO) {

        User user = new User( );
        user.setUsername(customerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole("CUSTOMER");


        Customer customer = new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer.setUser(user);
        user.setCustomer(customer);


        authRepository.save(user);
        customerRepository.save(customer);
    }


    public void updateCustomer(CustomerDTO customerDTO,Integer auth_id) {
        User user = authRepository.findUserById(auth_id);
        if (user == null) {
            throw new ApiException("User not found");
        }


        user.setUsername(customerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());


        user.getCustomer().setPhoneNumber(customerDTO.getPhoneNumber());


        authRepository.save(user);

    }

    public void deleteCustomer( Integer auth_id ) {
        User user = authRepository.findUserById(auth_id);
       Customer customer = customerRepository.findCustomerById(auth_id);

       if (user.getId() != auth_id) {
           throw new ApiException("Customer id does not match");
       }
       authRepository.delete(user);
       customerRepository.delete(customer);

    }



}


