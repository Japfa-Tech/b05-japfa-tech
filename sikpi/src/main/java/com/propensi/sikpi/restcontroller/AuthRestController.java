package com.propensi.sikpi.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propensi.sikpi.DTO.UserMapper;
import com.propensi.sikpi.DTO.request.LoginFormDTO;
import com.propensi.sikpi.DTO.request.LoginJwtRequestDTO;
import com.propensi.sikpi.DTO.response.ErrorResponseDTO;
import com.propensi.sikpi.DTO.response.LoginJwtResponseDTO;
import com.propensi.sikpi.service.RoleService;
import com.propensi.sikpi.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/auth/login-jwt-webadmin")
    public ResponseEntity<?> loginJwtAdmin(@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) {
        try {
            String jwtToken = userService.loginJwtAdmin(loginJwtRequestDTO);
            return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginFormDTO loginRequest) {
        try {
            String jwtToken = userService.loginJwtUser(loginRequest);
    
            if (jwtToken != null) {
                // Successful login, return the JWT token
                return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
            } else {
                // Incorrect credentials, return a 403 Forbidden response
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponseDTO(false, "Username atau password salah"));
            }
    
        } catch (Exception e) {
            // Other exceptions, return a bad request response
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
