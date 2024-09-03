package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    //Employee Model:
    //• id: Generated automatically.
    @Id
    private Integer id;

    //• position:
    //- Cannot be null.
    @NotEmpty(message = "Position should not be empty!")
    @Column(columnDefinition = "varchar(50) not null")
    private String position;


    //• salary:
    //- Cannot be null.
    //- Must be a non-negative decimal number.
    @NotNull(message = "Salary should not be null!")
    @Column(columnDefinition = "DOUBLE not null")
    @Positive
    private double salary;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

}
