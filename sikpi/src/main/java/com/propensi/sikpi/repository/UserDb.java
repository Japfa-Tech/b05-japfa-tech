package com.propensi.sikpi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.Role;
import com.propensi.sikpi.model.UserModel;
import java.util.List;
import java.util.Optional;



@Repository
public interface UserDb extends JpaRepository<UserModel, Long>{
    UserModel findByUsername(String username);
    List<UserModel> findByDivisi(String divisi);
    Optional<UserModel> findByRole(Role role);
}
