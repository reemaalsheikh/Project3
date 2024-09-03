package com.example.project3.Controller;

import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }


    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity delete( @PathVariable Integer user_id) {
        authService.delete(user_id);
        return ResponseEntity.status(200).body("User deleted successfully!");
    }

}
