package pl.finances.finances_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.requestsAndResponsesDto.RegisterAccountDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.LoginAccountDTO;
import pl.finances.finances_app.repositories.AccountRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import pl.finances.finances_app.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody LoginAccountDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody RegisterAccountDTO registerDTO) {
        if (accountRepository.existsByUsername(registerDTO.getUsername())) {
            return "Error: Username is already taken!";
        }
        AccountEntity newUser = new AccountEntity(
                registerDTO.getUsername(),
                encoder.encode(registerDTO.getPassword()),
                registerDTO.getBalance(),
                "USER"
        );
        accountRepository.save(newUser);
        return "User registered successfully!";
    }
}
