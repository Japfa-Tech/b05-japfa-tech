package com.propensi.sikpi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.Role;
import com.propensi.sikpi.repository.RoleDb;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDb roleDb;

    @Override
    public List<Role> getAllList() {
        return roleDb.findAll();
    }

    @Override
    public Role getRoleByRoleName(String name) {
        System.out.println(name);
        Optional<Role> role = roleDb.findByRole(name);
        System.out.println(role.get().getRole());
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

    @Override
    public void createRole() {
        // TODO Auto-generated method stub
        if (roleDb.findByRole("Admin") == null) {
            Role adminRole = new Role();
            adminRole.setRole("Admin");
            roleDb.save(adminRole);
        }

        if (roleDb.findByRole("User") == null) {
            Role userRole = new Role();
            userRole.setRole("User");
            roleDb.save(userRole);
        }
    }
}
