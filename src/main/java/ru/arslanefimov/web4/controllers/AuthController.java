package ru.arslanefimov.web4.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arslanefimov.web4.dto.requests.UserRequestDTO;
import ru.arslanefimov.web4.services.AuthService;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO request, HttpServletResponse response){
        return authService.register(request, response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO request, HttpServletResponse response){
        return authService.login(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        return authService.logout(response);
    }

}
