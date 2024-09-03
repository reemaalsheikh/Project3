package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;



    public List<User> getAllUsers(){
        return authRepository.findAll();
    }



    public void delete (Integer user_id) {
        User user1 = authRepository.findUserById(user_id);

        if(user1 == null) {
            throw new ApiException("User id not found");
        }

        authRepository.deleteById(user_id);
    }



}
