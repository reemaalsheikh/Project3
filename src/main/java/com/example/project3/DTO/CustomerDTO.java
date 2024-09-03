package com.example.project3.DTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {


    @NotEmpty(message ="Phone number cannot be null")
    @Pattern(regexp = "^(05|0)[0-9]{8}$" ,message = " Phone number Must start with 05, consists of exactly 10 digits")
    private String phoneNumber;



    @NotEmpty(message = "username should not be EMPTY!")
    @Size(min=4 , max=10 , message = "user name size should be between 4 - 10")
    private String username;


    @NotEmpty(message = "password should not be empty!")
   // @Size(min=6 , message = "Length must be at least 6 characters")
    private String password;


    @NotEmpty(message = "name should not be empty!")
    @Size(min=2 , max = 20, message = "Length must be between 2 and 20 characters")
    private String name;



    @Email
    @NotEmpty(message = "Email should not be EMPTY!")
    private String email;


   // @NotEmpty(message = "Role should not be EMPTY!")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role has 3 valid inputs only(CUSTOMER,EMPLOYEE,ADMIN).")
    private String role;


}
