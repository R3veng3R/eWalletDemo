package com.kn.eWallet.controller;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.dto.UserDTO;
import com.kn.eWallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser());
    }
}
