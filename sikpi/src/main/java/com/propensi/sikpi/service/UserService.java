package com.propensi.sikpi.service;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
import com.propensi.sikpi.DTO.request.LoginFormDTO;
import com.propensi.sikpi.DTO.request.LoginJwtRequestDTO;
import com.propensi.sikpi.model.UserModel;
import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO);
    String loginJwtUser(LoginFormDTO loginFormDTO);

    List<UserModel> getAllUser();

    UserModel getUserById(Long id);

    // void saveUser(Cabinet cabinet);

    UserModel updateUser(UserModel user);

    void changePassword(UserModel user, String password);
}
