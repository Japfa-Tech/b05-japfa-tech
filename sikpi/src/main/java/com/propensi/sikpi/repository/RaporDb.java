package com.propensi.sikpi.repository;

import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RaporDb extends JpaRepository<Rapor, Long> {
    Optional<Rapor> findByEvaluatedUser(UserModel evaluatedUser);

    List<Rapor> findBySignPenyetujuFalse();

    List<Rapor> findBySignPenilaiFalse();
}