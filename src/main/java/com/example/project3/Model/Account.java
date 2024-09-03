package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//Account Model:
public class Account {

    //• id: Generated automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //• accountNumber:
    //- Cannot be null.
    //- Must follow a specific format (e.g., "XXXX-XXXX-XXXX-XXXX").
    @NotEmpty(message = "Account Number should not be Empty!")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$")
    @Column(columnDefinition = "varchar (16) not null unique")
    private String accountNumber;


    //• balance:
    //- Cannot be null.
    //- Must be a non-negative decimal number.
    @NotNull(message = "Balance should not be null!")
    @Column(columnDefinition = "DOUBLE not null")
    @Positive
    private double balance;


    //• isActive:
    //- By default, false.
    @NotEmpty(message = "isActive should not be empty!")
    @Column(columnDefinition = "boolean default false")
    @AssertFalse
    private boolean isActive;



    @ManyToOne
   // @JoinColumn(name= "Customer_id" , referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

}
