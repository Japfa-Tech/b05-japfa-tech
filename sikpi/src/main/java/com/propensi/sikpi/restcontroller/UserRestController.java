package com.propensi.sikpi.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.propensi.sikpi.DTO.UserMapper;
import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
import com.propensi.sikpi.DTO.response.CreateUserResponseDTO;
import com.propensi.sikpi.service.UserService;
import com.propensi.sikpi.model.UserModel;


@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/user/add", consumes =  MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> addUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {
            UserModel userModel = userMapper.createUserRequestDTOToUserModel(createUserRequestDTO);
            userModel = userService.addUser(userModel);

            CreateUserResponseDTO createUserResponseDTO = userMapper.createUserResponseDTOToUserModel(userModel);
            createUserRequestDTO.setRole(userModel.getRole().getRole());
            return new ResponseEntity<>(createUserResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }

}
