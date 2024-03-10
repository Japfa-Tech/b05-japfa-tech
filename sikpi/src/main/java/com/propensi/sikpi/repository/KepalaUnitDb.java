package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.UserModel;

@Repository
public interface KepalaUnitDb extends JpaRepository<KepalaUnit, Long> {

}
