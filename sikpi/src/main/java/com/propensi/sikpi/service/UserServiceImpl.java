package com.propensi.sikpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
import com.propensi.sikpi.DTO.request.LoginFormDTO;
import com.propensi.sikpi.DTO.request.LoginJwtRequestDTO;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.security.jwt.JwtUtils;

import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.repository.DokumenDb;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    DokumenDb dokumenDb;

    @Override
    @Transactional
    public void changePassword(UserModel user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);
        try {
            userDb.save(user);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            System.err.println("Failed to update password: " + e.getMessage());
        }
    }

    @Override
    public UserModel addUser(UserModel user) {
        user.setRole(roleService.getRoleByRoleName(user.getRole().getRole()));
        String hashedPass = encrypt(user.getPassword());
        user.setPassword(hashedPass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO) {
        String username = loginJwtRequestDTO.getUsername();
        String name = loginJwtRequestDTO.getName();

        UserModel user = userDb.findByUsername(username);

        if (user == null) {
            user = new UserModel();
            user.setNamaLengkap(name);
            user.setPassword("admin123");
            user.setUsername(username);
            user.setRole(roleService.getRoleByRoleName("Admin"));
            userDb.save(user);
        }

        return jwtUtils.generateJwtToken(loginJwtRequestDTO.getUsername());
    }

    @Override
    public String loginJwtUser(LoginFormDTO loginFormDTO) {
        String username = loginFormDTO.getUsername();
        String providedPassword = loginFormDTO.getPassword();

        UserModel user = userDb.findByUsername(username);
        System.out.println(user.getNamaLengkap());
        System.out.println(user);

        if (user != null) {
            // Validate the provided password with the stored hashed password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(providedPassword, user.getPassword())) {
                return jwtUtils.generateJwtToken(username);
            }
        }

        return null;
    } // Return null if the user doesn't exist or the password is incorrect

    @Override
    public List<UserModel> getAllUser() {
        return userDb.findAll();
    }

    @Override
    public UserModel getUserById(Long id) {
        for (UserModel user : getAllUser()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // @Override
    // public void saveUser(Cabinet cabinet) {
    // cabinetDb.save(cabinet);
    // }

    @Override
    public UserModel updateUser(UserModel userFromDTO) {
        UserModel user = getUserById(userFromDTO.getId());
        for (Dokumen dok : user.getListDokumen()) {
            dokumenDb.delete(dok);
        }
        for (Dokumen dok : userFromDTO.getListDokumen()) {
            Dokumen newDok = new Dokumen();
            newDok.setNamaDokumen(dok.getNamaDokumen());
            newDok.setIdUser(user);
            newDok.setStatus(0);
            newDok.setUploadedDate(LocalDateTime.now());
            dokumenDb.save(newDok);
        }
        return user;
    }

}
