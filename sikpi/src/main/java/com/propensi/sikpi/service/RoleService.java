package com.propensi.sikpi.service;

import java.util.List;

import com.propensi.sikpi.model.Role;

public interface RoleService {
    List<Role> getAllList();
    Role getRoleByRoleName(String name);
    void createRole();
}
