package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

//    • id: Generated automatically.
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

//            • username:
//            - Cannot be null.
//            - Length must be between 4 and 10 characters.
@NotEmpty(message = "username should not be EMPTY!")
@Size(min=4 , max=10 , message = "user name size should be between 4 - 10")
@Column(columnDefinition = "varchar(50) not null unique")
private String username;


//            • password:
//            - Cannot be null.
//            - Length must be at least 6 characters.
    @NotEmpty(message = "password should not be empty!")
   // @Size(min=6 , message = "Length must be at least 6 characters")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

//• name:
//            - Cannot be null.
//            - Length must be between 2 and 20 characters.
//    Must be unique.
@NotEmpty(message = "name should not be empty!")
@Size(min=2 , max = 20, message = "Length must be between 2 and 20 characters")
@Column(columnDefinition = "varchar(50) not null")
    private String name;


//• email:
// - Must be a valid email format.
// - Must be unique.

@Email
@NotEmpty(message = "Email should not be EMPTY!")
@Column(columnDefinition = "varchar(20) not null unique")
private String email;

//• role:
//- Must be either "CUSTOMER" , "EMPLOYEE" or "ADMIN" only.

//@NotEmpty(message = "Role should not be EMPTY!")
@Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role has 3 valid inputs only(CUSTOMER,EMPLOYEE,ADMIN).")
//@Column(columnDefinition = "varchar(15) check (role='ADMIN' or role='CUSTOMER' or role='EMPLOYEE')")
private String role;


    @OneToOne(cascade = CascadeType.ALL , mappedBy ="user" )
    @PrimaryKeyJoinColumn
    private Customer customer;


    @OneToOne(cascade = CascadeType.ALL , mappedBy ="user" )
    @PrimaryKeyJoinColumn
    private Employee employee;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
