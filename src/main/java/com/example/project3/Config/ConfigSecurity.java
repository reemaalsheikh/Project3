package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //User name
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        //Password
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .authenticationProvider(daoauthenticationProvider())
        .authorizeHttpRequests()
          //get all users
         .requestMatchers("/api/v1/auth/get").hasAuthority("ADMIN")
         .requestMatchers("/api/v1/auth//delete/{user_id}" ).hasAuthority("ADMIN")

         //Customer register
         .requestMatchers("/api/v1/customer/get").hasAuthority("ADMIN")
         .requestMatchers("/api/v1/customer/register").permitAll()
         .requestMatchers("/api/v1/customer/update" ).hasAuthority("CUSTOMER")
         .requestMatchers("/api/V1/customer/delete" ).hasAuthority("CUSTOMER")

          //Employee register
         .requestMatchers("/api/v1/employee/get").hasAuthority("ADMIN")
         .requestMatchers("/api/v1/employee/register").permitAll()
         .requestMatchers("/api/v1/employee/update" ).hasAuthority("EMPLOYEE")
         .requestMatchers("/api/v1/employee/delete" ).hasAuthority("EMPLOYEE")

         //Account
         .requestMatchers("/api/v1/account/get").hasAuthority("ADMIN")
         .requestMatchers("/api/v1/account/create").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/update/{account_id}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/delete/{account_id}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/activate/{account_id}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/details/{account_id}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/get-c-accounts").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/deposit/{account_id}/{amount}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/withdraw/{account_id}/{amount}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/transferFunds/{fromAccount_id}/{toAccount_id}/{amount}").hasAuthority("CUSTOMER")
         .requestMatchers("/api/v1/account/blockAccount/{account_id}").hasAuthority("ADMIN")







                .anyRequest().authenticated()
        .and()
        .logout().logoutUrl("/api/v1/auth/logout")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .and()
        .httpBasic();

        return http.build();
    }





}
