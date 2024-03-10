package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.UserModel;

import jakarta.transaction.Transactional;

import java.util.*;

@Repository
@Transactional
public interface DokumenDb extends JpaRepository<Dokumen, Long> {
    List<Dokumen> findAllByIdUser(UserModel idUser);

}
